package com.ssafy.ssapilogue.core.repository;

import com.ssafy.ssapilogue.core.domain.Project;
import com.ssafy.ssapilogue.core.domain.ProjectStack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectStackRepository extends JpaRepository<ProjectStack, Long> {

    List<ProjectStack> findByProject(Project project);
}
