package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateProjectReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindCommentResDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectDetailResDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectResDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectTitleResDto;
import com.ssafy.ssapilogue.api.exception.CustomException;
import com.ssafy.ssapilogue.api.exception.ErrorCode;
import com.ssafy.ssapilogue.core.domain.*;
import com.ssafy.ssapilogue.core.repository.*;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TechStackRepository techStackRepository;
    private final ProjectStackRepository projectStackRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final AnonymousMemberRepository anonymousMemberRepository;
    private final LikedRepository likedRepository;
    private final BookmarkRepsitory bookmarkRepsitory;
    private final ProjectCommentRepository projectCommentRepository;
    private final SurveyRepository surveyRepository;
    private final SurveyService surveyService;

    private static final int HANGEUL_BASE = 0xAC00;    // '가'
    private static final int HANGEUL_END = 0xD7AF;
    // 이하 ja, mo는 단독으로 입력된 자모에 대해 적용
    private static final int JA_BASE = 0x3131;
    private static final int MO_BASE = 0x314F;

    private static final String[] CHO = {"ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ", "ㅅ","ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};
    private static final String[] JUNG = {"ㅏ","ㅐ","ㅑ","ㅒ","ㅓ","ㅔ","ㅕ","ㅖ","ㅗ","ㅘ", "ㅙ","ㅚ","ㅛ","ㅜ","ㅝ","ㅞ","ㅟ","ㅠ","ㅡ","ㅢ","ㅣ"};
    private static final String[] JONG = {"","ㄱ","ㄲ","ㄳ","ㄴ","ㄵ","ㄶ","ㄷ","ㄹ","ㄺ","ㄻ","ㄼ", "ㄽ","ㄾ","ㄿ","ㅀ","ㅁ","ㅂ","ㅄ","ㅅ","ㅆ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};


    @Value("${projectImg.path}")
    private String uploadFolder;

    // 프로젝트 전체조회
    @Override
    public List<FindProjectResDto> findProjects(String userEmail) {
        List<Project> projects = projectRepository.findAllByOrderByIdDesc();

        List<FindProjectResDto> findProjectResDtos = new ArrayList<>();
        if (userEmail.isEmpty()) {
            for (Project project : projects) {
                findProjectResDtos.add(new FindProjectResDto(project, false));
            }
        } else {
            User user = userRepository.findByEmail(userEmail);
            for (Project project : projects) {
                Optional<Bookmark> bookmark = bookmarkRepsitory.findByUserAndProject(user, project);

                Boolean isBookmarked = false;
                if (bookmark.isPresent()) {
                    isBookmarked = true;
                }

                findProjectResDtos.add(new FindProjectResDto(project, isBookmarked));
            }
        }

        return findProjectResDtos;
    }

    // 프로젝트 등록
    @Override
    public Long createProject(CreateProjectReqDto createProjectReqDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) throw new CustomException(ErrorCode.NO_USER);

        Project project = Project.builder()
                .title(createProjectReqDto.getTitle())
                .splitTitle(getSplitTitle(createProjectReqDto.getTitle()))
                .introduce(createProjectReqDto.getIntroduce())
                .category(Category.valueOf(createProjectReqDto.getCategory()))
                .deployAddress(createProjectReqDto.getDeployAddress())
                .gitAddress(createProjectReqDto.getGitAddress())
                .thumbnail(createProjectReqDto.getThumbnail())
                .readme(createProjectReqDto.getReadme())
                .user(user)
                .build();

        if (createProjectReqDto.getReadmeCheck() == 0) {
            try {
                String gitAddress = createProjectReqDto.getGitAddress();
                int idx = gitAddress.indexOf("github.com");
                String gitRepo = gitAddress.substring(idx+10);

                URI uri = UriComponentsBuilder
                        .fromUriString("https://api.github.com/repos")
                        .path(gitRepo + "/readme")
                        .build()
                        .toUri();

                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(response.getBody());
                String content = obj.get("content").toString();
                content = content.replace("\n","");

                byte[] decoded = Base64.getDecoder().decode(content);
                String readmeContent = new String(decoded, StandardCharsets.UTF_8);

                String absolutePath = "https://github.com/" + gitRepo + "/raw/master/";
                while (readmeContent.contains("(README.assets")) {
                    int relativeIdx = readmeContent.indexOf("(README.assets");
                    String tempContent = readmeContent.substring(0, relativeIdx+1) + absolutePath + readmeContent.substring(relativeIdx+1);
                    readmeContent = tempContent;
                }

                project.updateReadme(readmeContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Project saveProject = projectRepository.save(project);

        // 멤버 등록
        List<String> members = createProjectReqDto.getMember();
        for (String nickname : members) {
            Optional<User> findMember = userRepository.findByNickname(nickname);

            if (findMember.isPresent()) {
                ProjectMember projectMember = ProjectMember.builder()
                        .project(saveProject)
                        .user(findMember.get())
                        .build();
                projectMemberRepository.save(projectMember);
            } else {
                AnonymousMember anonymousMember = AnonymousMember.builder()
                        .nickname(nickname)
                        .project(saveProject)
                        .build();
                anonymousMemberRepository.save(anonymousMember);
            }
        }

        // 기술스택 등록
        List<String> techStacks = createProjectReqDto.getTechStack();
        for (String stackName : techStacks) {
            Optional<TechStack> findTechStack = techStackRepository.findByName(stackName);

            if (findTechStack.isPresent()) {
                TechStack techStack = findTechStack.get();

                ProjectStack projectStack = ProjectStack.builder()
                        .project(saveProject)
                        .techStack(techStack)
                        .build();
                projectStackRepository.save(projectStack);
            } else {
                TechStack newTechStack = TechStack.builder()
                        .name(stackName)
                        .build();
                TechStack saveTechStack = techStackRepository.save(newTechStack);

                ProjectStack projectStack = ProjectStack.builder()
                        .project(saveProject)
                        .techStack(saveTechStack)
                        .build();
                projectStackRepository.save(projectStack);
            }
        }

        return saveProject.getId();
    }

    // 프로젝트 상세조회
    @Override
    public FindProjectDetailResDto findProjectDetail(Long projectId, String userEmail) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

        User user = userRepository.findByEmail(userEmail);
        if (user == null) throw new CustomException(ErrorCode.NO_USER);

        Optional<Liked> liked = likedRepository.findByUserAndProject(user, project);
        Boolean isLiked = false;
        if (liked.isPresent()) {
            isLiked = true;
        }

        Optional<Bookmark> bookmark = bookmarkRepsitory.findByUserAndProject(user, project);
        Boolean isBookmarked = false;
        if (bookmark.isPresent()) {
            isBookmarked = true;
        }

        List<Project> anotherProjects = projectRepository.findByCategoryOrderByRandom(project.getCategory().toString(), projectId);
        List<FindProjectResDto> findProjectResDtos = new ArrayList<>();
        for (Project anotherProject : anotherProjects) {
            Optional<Bookmark> anotherBookmark = bookmarkRepsitory.findByUserAndProject(user, anotherProject);

            Boolean isAnotherBookmarked = false;
            if (anotherBookmark.isPresent()) {
                isAnotherBookmarked = true;
            }

            findProjectResDtos.add(new FindProjectResDto(anotherProject, isAnotherBookmarked));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<ProjectComment> projectComments = projectCommentRepository.findByProjectOrderByIdDesc(project);

        List<FindCommentResDto> commentList = new ArrayList<>();
        for (ProjectComment projectComment : projectComments) {
            FindCommentResDto findCommentResDto = new FindCommentResDto(projectComment);
            findCommentResDto.setCreatedAt(projectComment.getCreatedAt().format(formatter));
            commentList.add(findCommentResDto);
        }

        project.increaseHits();
        return new FindProjectDetailResDto(project, isLiked, isBookmarked, findProjectResDtos, commentList);
    }

    // 프로젝트 수정
    @Override
    public void updateProject(Long projectId, CreateProjectReqDto createProjectReqDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

        project.update(createProjectReqDto, getSplitTitle(createProjectReqDto.getTitle()));

        // 멤버 수정
        projectMemberRepository.deleteByProject(project);
        anonymousMemberRepository.deleteByProject(project);

        List<String> members = createProjectReqDto.getMember();
        for (String nickname : members) {
            Optional<User> findMember = userRepository.findByNickname(nickname);

            if (findMember.isPresent()) {
                ProjectMember projectMember = ProjectMember.builder()
                        .project(project)
                        .user(findMember.get())
                        .build();
                projectMemberRepository.save(projectMember);
            } else {
                AnonymousMember anonymousMember = AnonymousMember.builder()
                        .nickname(nickname)
                        .project(project)
                        .build();
                anonymousMemberRepository.save(anonymousMember);
            }
        }

        // 기술스택 수정
        projectStackRepository.deleteByProject(project);

        List<String> techStacks = createProjectReqDto.getTechStack();
        for (String stackName : techStacks) {
            Optional<TechStack> findTechStack = techStackRepository.findByName(stackName);

            if (findTechStack.isPresent()) {
                TechStack techStack = findTechStack.get();

                ProjectStack projectStack = ProjectStack.builder()
                        .project(project)
                        .techStack(techStack)
                        .build();
                projectStackRepository.save(projectStack);
            } else {
                TechStack newTechStack = TechStack.builder()
                        .name(stackName)
                        .build();
                TechStack saveTechStack = techStackRepository.save(newTechStack);

                ProjectStack projectStack = ProjectStack.builder()
                        .project(project)
                        .techStack(saveTechStack)
                        .build();
                projectStackRepository.save(projectStack);
            }
        }
    }

    // 프로젝트 삭제
    @Override
    public void deleteProject(Long projectId, String userEmail) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

        int likeCnt = project.getLikedList().size();
        if (project.getUser().getEmail().equals(userEmail)) {
            project.getUser().changeLikes(-likeCnt);

            // 설문조사 삭제
            List<Survey> surveys = surveyRepository.findAllByProjectId(project.getId());
            for (Survey survey : surveys) {
                surveyService.deleteSurvey(survey.getId());
            }

            projectRepository.deleteById(projectId);
        }
    }

    // 리드미 갱신
    @Override
    public void updateReadme(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

        try {
            String gitAddress = project.getGitAddress();
            int idx = gitAddress.indexOf("github.com");
            String gitRepo = gitAddress.substring(idx+10);

            URI uri = UriComponentsBuilder
                    .fromUriString("https://api.github.com/repos")
                    .path(gitRepo + "/readme")
                    .build()
                    .toUri();

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(response.getBody());
            String content = obj.get("content").toString();
            content = content.replace("\n","");

            byte[] decoded = Base64.getDecoder().decode(content);
            String readmeContent = new String(decoded, StandardCharsets.UTF_8);

            String absolutePath = "https://github.com/" + gitRepo + "/raw/master/";
            while (readmeContent.contains("(README.assets")) {
                int relativeIdx = readmeContent.indexOf("(README.assets");
                String tempContent = readmeContent.substring(0, relativeIdx+1) + absolutePath + readmeContent.substring(relativeIdx+1);
                readmeContent = tempContent;
            }

            project.updateReadme(readmeContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 이미지 업로드
    @Override
    public String uploadImage(MultipartFile multipartFile, String userEmail) {
        String imageFileName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        if (multipartFile.getSize() != 0) {
            try {
                Files.write(imageFilePath, multipartFile.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "http://k6c104.p.ssafy.io/images/projectImg/" + imageFileName;
    }

    // 제목으로 프로젝트 검색
    @Override
    public List<FindProjectResDto> searchProjectsByTitle(String keyword, String userEmail) {
        List<Project> projects = projectRepository.findBySplitTitleContainingOrderByIdDesc(getSplitTitle(keyword));

        List<FindProjectResDto> findProjectResDtos = new ArrayList<>();
        if (userEmail.isEmpty()) {
            for (Project project : projects) {
                findProjectResDtos.add(new FindProjectResDto(project, false));
            }
        } else {
            User user = userRepository.findByEmail(userEmail);
            for (Project project : projects) {
                Optional<Bookmark> bookmark = bookmarkRepsitory.findByUserAndProject(user, project);

                Boolean isBookmarked = false;
                if (bookmark.isPresent()) {
                    isBookmarked = true;
                }

                findProjectResDtos.add(new FindProjectResDto(project, isBookmarked));
            }
        }

        return findProjectResDtos;
    }

    // 프로젝트 제목 자동완성
    @Override
    public List<FindProjectTitleResDto> searchProjectTitles(String keyword) {
        List<Project> projects = projectRepository.findBySplitTitleContainingOrderByIdDesc(getSplitTitle(keyword));

        return projects.stream().map(FindProjectTitleResDto::new).collect(Collectors.toList());
    }

    private String getSplitTitle(String title) {
        StringBuilder sb = new StringBuilder();

        for (char c : title.toCharArray()) {
            if((c <= 10 && c <= 13) || c == 32) {
                sb.append(c);
                continue;
            } else if (c >= JA_BASE && c <= JA_BASE + 36) {
                sb.append(c);
                continue;
            } else if (c >= MO_BASE && c <= MO_BASE + 58) {
                sb.append((char)0);
                continue;
            } else if (c >= HANGEUL_BASE && c <= HANGEUL_END){
                int choInt = (c - HANGEUL_BASE) / 28 / 21;
                int jungInt = ((c - HANGEUL_BASE) / 28) % 21;
                int jongInt = (c - HANGEUL_BASE) % 28;
                char cho = (char) (choInt);
                char jung = (char) (jungInt);
                char jong = jongInt != 0 ? (char) (jongInt) : 0;

                sb.append(CHO[cho]);
                sb.append(JUNG[jung]);
                if (jong != 0) {
                    sb.append(JONG[jong]);
                }
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }
}
