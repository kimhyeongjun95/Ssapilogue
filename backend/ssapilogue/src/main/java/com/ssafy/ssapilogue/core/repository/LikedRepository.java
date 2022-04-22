package com.ssafy.ssapilogue.core.repository;

import com.ssafy.ssapilogue.core.domain.Liked;
import com.ssafy.ssapilogue.core.domain.Project;
import com.ssafy.ssapilogue.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikedRepository extends JpaRepository<Liked, Long> {

    Optional<Liked> findByUserAndProject(User user, Project project);
    void deleteByUserAndProject(User user, Project project);
}
