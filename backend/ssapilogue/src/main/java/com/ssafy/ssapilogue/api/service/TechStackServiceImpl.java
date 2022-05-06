package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.response.FindProjectResDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectTitleResDto;
import com.ssafy.ssapilogue.core.domain.*;
import com.ssafy.ssapilogue.core.repository.BookmarkRepsitory;
import com.ssafy.ssapilogue.core.repository.ProjectStackRepository;
import com.ssafy.ssapilogue.core.repository.TechStackRepository;
import com.ssafy.ssapilogue.core.repository.UserRepository;
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
public class TechStackServiceImpl implements TechStackService{

    private final TechStackRepository techStackRepository;
    private final ProjectStackRepository projectStackRepository;
    private final UserRepository userRepository;
    private final BookmarkRepsitory bookmarkRepsitory;

    // 기술스택으로 프로젝트 검색
    @Override
    public List<FindProjectResDto> searchProjectsByTechStack(String keyword, String userEmail) {
        List<TechStack> techStacks = techStackRepository.findByNameContaining(keyword);

        List<ProjectStack> projectStacks = new ArrayList<>();
        for (TechStack techStack : techStacks) {
            projectStacks.addAll(projectStackRepository.findByTechStack(techStack));
        }

        List<Project> projects = projectStacks.stream().map(ProjectStack::getProject).collect(Collectors.toList());

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

    // 정확한 기술스택으로 프로젝트 검색
    @Override
    public List<FindProjectResDto> searchProjectsByTechStackSpecific(String keyword, String userEmail) {
        TechStack techStack = techStackRepository.findByName(keyword).get();
        List<ProjectStack> projectStacks = projectStackRepository.findByTechStack(techStack);
        List<Project> projects = projectStacks.stream().map(ProjectStack::getProject).collect(Collectors.toList());

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

    // 기술스택으로 프로젝트 제목 자동완성
    @Override
    public List<FindProjectTitleResDto> searchProjectTitlesByTechStack(String keyword) {
        List<TechStack> techStacks = techStackRepository.findByNameContaining(keyword);

        List<ProjectStack> projectStacks = new ArrayList<>();
        for (TechStack techStack : techStacks) {
            projectStacks.addAll(projectStackRepository.findByTechStack(techStack));
        }

        List<Project> projects = projectStacks.stream().map(ProjectStack::getProject).collect(Collectors.toList());

        return projects.stream().map(FindProjectTitleResDto::new).collect(Collectors.toList());
    }

    // 기술스택 자동완성
    @Override
    public List<String> searchTechStacks(String keyword) {
        List<TechStack> techStacks = techStackRepository.findByNameContaining(keyword);

        return techStacks.stream().map(TechStack::getName).collect(Collectors.toList());
    }
}
