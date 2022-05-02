package com.ssafy.ssapilogue.api.service;

import java.util.List;

public interface UserInfoService {

    void splitUserInfo();
    List<String> searchUserInfos(String keyword);
}
