package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateProjectReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectDetailResDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectResDto;
import com.ssafy.ssapilogue.core.domain.Category;
import com.ssafy.ssapilogue.core.domain.Project;
import com.ssafy.ssapilogue.core.domain.ProjectStack;
import com.ssafy.ssapilogue.core.domain.TechStack;
import com.ssafy.ssapilogue.core.repository.ProjectRepository;
import com.ssafy.ssapilogue.core.repository.ProjectStackRepository;
import com.ssafy.ssapilogue.core.repository.TechStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;
    private final TechStackRepository techStackRepository;
    private final ProjectStackRepository projectStackRepository;

    // 프로젝트 전체조회
    @Override
    public List<FindProjectResDto> findProjects(String category) {
        List<Project> projects = null;

        if (category.equals("전체")) {
            projects = projectRepository.findAllByOrderByIdDesc();
        } else {
            projects = projectRepository.findByCategoryOrderByIdDesc(Category.valueOf(category));
        }

        return projects.stream().map(FindProjectResDto::new).collect(Collectors.toList());
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

        List<String> techStacks = createProjectReqDto.getTechStack();
        for (String stackName : techStacks) {
            Optional<TechStack> findTechStack = techStackRepository.findByName(stackName);

            if (findTechStack.isEmpty()) {
                TechStack newTechStack = TechStack.builder()
                        .name(stackName)
                        .build();
                TechStack saveTechStack = techStackRepository.save(newTechStack);

                ProjectStack projectStack = ProjectStack.builder()
                        .project(saveProject)
                        .techStack(saveTechStack)
                        .build();
                projectStackRepository.save(projectStack);
            } else {
                TechStack techStack = findTechStack.get();

                ProjectStack projectStack = ProjectStack.builder()
                        .project(saveProject)
                        .techStack(techStack)
                        .build();
                projectStackRepository.save(projectStack);
            }
        }

        return saveProject.getId();
    }

    // 프로젝트 상세조회
    @Override
    public FindProjectDetailResDto findProject(Long projectId) {
        Project project = projectRepository.getById(projectId);
        project.increaseHits();

        List<ProjectStack> projectStacks = projectStackRepository.findByProject(project);
        List<TechStack> techStacks = projectStacks.stream()
                .map(ProjectStack::getTechStack).collect(Collectors.toList());

        return new FindProjectDetailResDto(project, techStacks);
    }

    // 프로젝트 수정
    @Override
    public void updateProject(Long projectId, CreateProjectReqDto createProjectReqDto) {
        Project project = projectRepository.getById(projectId);
        project.update(createProjectReqDto.getTitle(), createProjectReqDto.getIntroduce(),
                Category.valueOf(createProjectReqDto.getCategory()), createProjectReqDto.getDeployAddress(),
                createProjectReqDto.getGitAddress(), createProjectReqDto.getThumbnail(), createProjectReqDto.getReadme());

    }

    // 프로젝트 삭제
    @Override
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }
}
