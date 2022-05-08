package com.ssafy.ssapilogue.api.service;

import org.json.simple.parser.ParseException;

import java.util.List;

public interface UserInfoService {

    void splitUserInfo();
    List<String> searchUserInfos(String keyword);
    void getUserInfo() throws ParseException;
    String encrypt(byte[] mmId);
    String decrypt(byte[] mmId);
}
