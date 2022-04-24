package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateProjectReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindCommentResDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectDetailResDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectResDto;
import com.ssafy.ssapilogue.core.domain.*;
import com.ssafy.ssapilogue.core.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // 프로젝트 전체조회
    @Override
    public List<FindProjectResDto> findProjects(String standard, String category, String userId) {
        List<Project> projects = null;
        User user = userRepository.getById(userId);

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
    public Long createProject(CreateProjectReqDto createProjectReqDto) {
        Project project = Project.builder()
                .title(createProjectReqDto.getTitle())
                .introduce(createProjectReqDto.getIntroduce())
                .category(Category.valueOf(createProjectReqDto.getCategory()))
                .deployAddress(createProjectReqDto.getDeployAddress())
                .gitAddress(createProjectReqDto.getGitAddress())
                .thumbnail(createProjectReqDto.getThumbnail())
                .readme(createProjectReqDto.getReadme())
                .build();

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
    public FindProjectDetailResDto findProject(Long projectId, String userId) {
        Project project = projectRepository.getById(projectId);
        User user = userRepository.getById(userId);

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<ProjectComment> projectComments = projectCommentRepository.findByProjectOrderByIdDesc(project);

        List<FindCommentResDto> commentList = new ArrayList<>();
        for (ProjectComment projectComment : projectComments) {
            FindCommentResDto findCommentResDto = new FindCommentResDto(projectComment);
            findCommentResDto.setCreatedAt(projectComment.getCreatedAt().format(formatter));
            commentList.add(findCommentResDto);
        }

        project.increaseHits();
        return new FindProjectDetailResDto(project, isLiked, isBookmarked, commentList);
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
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }
}
