package com.ssafy.ssapilogue.core.repository;

import com.ssafy.ssapilogue.core.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    UserInfo findByUserId(String userId);
    List<UserInfo> findByNicknameContaining(@Param("keyword") String keyword);
}
