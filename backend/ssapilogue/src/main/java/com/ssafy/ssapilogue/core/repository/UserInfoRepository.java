package com.ssafy.ssapilogue.core.repository;

import com.ssafy.ssapilogue.core.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    UserInfo findByUserId(String userId);
}
