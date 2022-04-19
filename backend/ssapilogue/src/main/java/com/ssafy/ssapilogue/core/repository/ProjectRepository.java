package com.ssafy.ssapilogue.core.repository;

import com.ssafy.ssapilogue.core.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
