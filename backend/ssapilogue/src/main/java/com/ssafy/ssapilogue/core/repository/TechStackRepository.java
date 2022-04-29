package com.ssafy.ssapilogue.core.repository;

import com.ssafy.ssapilogue.core.domain.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TechStackRepository extends JpaRepository<TechStack, Long> {

    List<TechStack> findByNameContaining(String name);
    Optional<TechStack> findByName(String name);
}
