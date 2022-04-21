package com.ssafy.ssapilogue.core.repository;

import com.ssafy.ssapilogue.core.domain.Project;
import com.ssafy.ssapilogue.core.domain.ProjectStack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectStackRepository extends JpaRepository<ProjectStack, Long> {
    void deleteByProject(Project project);
}
