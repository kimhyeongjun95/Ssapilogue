package com.ssafy.ssapilogue.core.repository;

import com.ssafy.ssapilogue.core.domain.Project;
import com.ssafy.ssapilogue.core.domain.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    void deleteByProject(Project project);

    List<ProjectMember> findByProject(Project project);
}
