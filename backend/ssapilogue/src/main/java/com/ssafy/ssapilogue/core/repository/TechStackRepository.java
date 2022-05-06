package com.ssafy.ssapilogue.core.repository;

import com.ssafy.ssapilogue.core.domain.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TechStackRepository extends JpaRepository<TechStack, Long> {

    List<TechStack> findByNameContaining(@Param("name") String name);
    Optional<TechStack> findByName(String name);
}
