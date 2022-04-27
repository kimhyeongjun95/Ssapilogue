package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateProjectReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindCommentResDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectDetailResDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectResDto;
import com.ssafy.ssapilogue.core.domain.*;
import com.ssafy.ssapilogue.core.repository.*;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

@RequiredArgsConstructor
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TechStackRepository techStackRepository;
    private final ProjectStackRepository projectStackRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final AnonymousMemberRepository anonymousMemberRepository;
    private final LikedRepository likedRepository;
    private final BookmarkRepsitory bookmarkRepsitory;
    private final ProjectCommentRepository projectCommentRepository;

    @Value("${projectImg.path}")
    private String uploadFolder;

    // 프로젝트 전체조회
    @Override
    public List<FindProjectResDto> findProjects(String standard, String category, String userEmail) {
        List<Project> projects = null;
        User user = userRepository.findByEmail(userEmail);

        if (standard.equals("최신")) {
            if (category.equals("전체")) {
                projects = projectRepository.findAllByOrderByIdDesc();
            } else {
                projects = projectRepository.findByCategoryOrderByIdDesc(Category.valueOf(category));
            }
        } else if (standard.equals("인기")) {
            if (category.equals("전체")) {
                projects = projectRepository.findAllByOrderByLikesDesc();
            } else {
                projects = projectRepository.findByCategoryOrderByLikesDesc(Category.valueOf(category));
            }
        }

        List<FindProjectResDto> findProjectResDtos = new ArrayList<>();
        for (Project project : projects) {
            Optional<Bookmark> bookmark = bookmarkRepsitory.findByUserAndProject(user, project);

            Boolean isBookmarked = false;
            if (bookmark.isPresent()) {
                isBookmarked = true;
            }

            findProjectResDtos.add(new FindProjectResDto(project, isBookmarked));
        }

        return findProjectResDtos;
    }

    // 프로젝트 등록
    @Override
    public Long createProject(CreateProjectReqDto createProjectReqDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail);

        Project project = Project.builder()
                .title(createProjectReqDto.getTitle())
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
    public FindProjectDetailResDto findProject(Long projectId, String userEmail) {
        Project project = projectRepository.getById(projectId);
        User user = userRepository.findByEmail(userEmail);

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
        return new FindProjectDetailResDto(project, isLiked, isBookmarked, findProjectResDtos ,commentList);
    }

    // 프로젝트 수정
    @Override
    public void updateProject(Long projectId, CreateProjectReqDto createProjectReqDto) {
        Project project = projectRepository.getById(projectId);
        project.update(createProjectReqDto);

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
        Project project = projectRepository.getById(projectId);
        int likeCnt = project.getLikedList().size();
        if (project.getUser().getEmail().equals(userEmail)) {
            project.getUser().changeLikes(-likeCnt);
            projectRepository.deleteById(projectId);
        }
    }

    @Override
    public void updateReadme(Long projectId) {
        Project project = projectRepository.getById(projectId);
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
        User user = userRepository.findByEmail(userEmail);
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
}
