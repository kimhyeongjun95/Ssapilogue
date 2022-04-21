package com.ssafy.ssapilogue.core.repository;

import com.ssafy.ssapilogue.core.domain.AnonymousMember;
import com.ssafy.ssapilogue.core.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnonymousMemberRepository extends JpaRepository<AnonymousMember, Long> {

    void deleteByProject(Project project);
}
