package com.ssafy.ssapilogue.core.repository;

import com.ssafy.ssapilogue.core.domain.Category;
import com.ssafy.ssapilogue.core.domain.Project;
import com.ssafy.ssapilogue.core.queryrepository.ProjectQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectQueryRepository {

    List<Project> findAllByOrderByIdDesc();
    List<Project> findByCategoryOrderByIdDesc(Category category);
    List<Project> findBySplitTitleContainingOrderByIdDesc(@Param("keyword") String keyword);
}
