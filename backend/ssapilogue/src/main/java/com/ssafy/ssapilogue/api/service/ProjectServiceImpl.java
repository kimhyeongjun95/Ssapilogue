package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateProjectReqDto;
import com.ssafy.ssapilogue.core.domain.Category;
import com.ssafy.ssapilogue.core.domain.Project;
import com.ssafy.ssapilogue.core.domain.TechStack;
import com.ssafy.ssapilogue.core.repository.ProjectRepository;
import com.ssafy.ssapilogue.core.repository.TechStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;
    private final TechStackRepository techStackRepository;

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
}
