package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.core.domain.UserInfo;
import com.ssafy.ssapilogue.core.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;

    // 멤버 자동완성
    @Override
    public List<String> searchUserInfos(String keyword) {
        List<UserInfo> userInfos = userInfoRepository.findByNicknameContaining(keyword);

        return userInfos.stream().map(UserInfo::getNickname).collect(Collectors.toList());
    }
}
