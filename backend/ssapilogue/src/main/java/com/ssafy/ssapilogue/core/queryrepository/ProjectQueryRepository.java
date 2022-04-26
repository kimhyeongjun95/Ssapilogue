package com.ssafy.ssapilogue.core.queryrepository;

import com.ssafy.ssapilogue.core.domain.Category;
import com.ssafy.ssapilogue.core.domain.Project;

import java.util.List;

public interface ProjectQueryRepository {

    List<Project> findAllByOrderByLikesDesc();
    List<Project> findByCategoryOrderByLikesDesc(Category category);
    List<Project> findByCategoryOrderByRandom(String category, Long projectId);
}
