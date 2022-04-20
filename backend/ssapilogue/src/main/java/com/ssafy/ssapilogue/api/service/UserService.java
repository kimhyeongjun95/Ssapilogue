package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.LoginUserReqDto;
import com.ssafy.ssapilogue.api.dto.request.SignupUserReqDto;
import com.ssafy.ssapilogue.api.dto.response.SignupUserResDto;
import com.ssafy.ssapilogue.core.domain.User;

public interface UserService {

    SignupUserResDto signup(SignupUserReqDto signupUserReqDto);

    User login(LoginUserReqDto loginUserReqDto) throws Exception;
}
