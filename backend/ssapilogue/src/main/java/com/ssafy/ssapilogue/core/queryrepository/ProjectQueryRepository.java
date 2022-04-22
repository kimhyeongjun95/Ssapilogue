package com.ssafy.ssapilogue.core.queryrepository;

import com.ssafy.ssapilogue.core.domain.Category;
import com.ssafy.ssapilogue.core.domain.Liked;
import com.ssafy.ssapilogue.core.domain.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectQueryRepository {

    List<Project> findAllByOrderByLikesDesc();
    List<Project> findByCategoryOrderByLikesDesc(Category category);
}
