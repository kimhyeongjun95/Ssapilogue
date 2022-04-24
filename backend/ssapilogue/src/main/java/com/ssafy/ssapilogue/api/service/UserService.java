package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.LoginUserReqDto;
import com.ssafy.ssapilogue.api.dto.request.SignupUserReqDto;
import com.ssafy.ssapilogue.api.dto.request.UpdateUserReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindUserResDto;
import com.ssafy.ssapilogue.api.dto.response.SignupUserResDto;
import com.ssafy.ssapilogue.core.domain.User;

public interface UserService {

    SignupUserResDto signup(SignupUserReqDto signupUserReqDto);

    User login(LoginUserReqDto loginUserReqDto) throws Exception;

    void updateUser(UpdateUserReqDto updateUserReqDto);

    void deleteUser(String userId);

    FindUserResDto findUserProfile(String userId);
}
