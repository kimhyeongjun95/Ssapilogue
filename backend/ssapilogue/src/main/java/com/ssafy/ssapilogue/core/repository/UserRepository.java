package com.ssafy.ssapilogue.core.repository;

import com.ssafy.ssapilogue.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByEmail(String email);
}
