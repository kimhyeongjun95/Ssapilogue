package com.ssafy.ssapilogue.core.repository;

import com.ssafy.ssapilogue.core.domain.UserInfoSplit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserInfoSplitRepository extends JpaRepository<UserInfoSplit, Long> {

    List<UserInfoSplit> findBySplitNicknameContaining(@Param("keyword") String keyword);
}
