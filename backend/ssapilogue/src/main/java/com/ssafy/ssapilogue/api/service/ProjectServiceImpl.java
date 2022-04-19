package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateProjectReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectResDto;
import com.ssafy.ssapilogue.core.domain.Category;
import com.ssafy.ssapilogue.core.domain.Project;
import com.ssafy.ssapilogue.core.domain.TechStack;
import com.ssafy.ssapilogue.core.repository.ProjectRepository;
import com.ssafy.ssapilogue.core.repository.TechStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;
    private final TechStackRepository techStackRepository;

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

        List<String> techStack = createProjectReqDto.getTechStack();
        for (String stack : techStack) {
            TechStack newTechStack = TechStack.builder()
                    .name(stack)
                    .project(saveProject)
                    .build();
            techStackRepository.save(newTechStack);
        }

        return saveProject.getId();
    }

    // 프로젝트 삭제
    @Override
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }
}
