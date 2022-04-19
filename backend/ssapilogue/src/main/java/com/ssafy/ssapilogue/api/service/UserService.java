package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.UserLoginDto;
import com.ssafy.ssapilogue.api.dto.request.UserRequestDto;
import com.ssafy.ssapilogue.api.dto.response.UserSimpleResponseDto;
import com.ssafy.ssapilogue.core.domain.User;

public interface UserService {

    UserSimpleResponseDto signup(UserRequestDto userDto);

    User login(UserLoginDto userLoginDto) throws Exception;
}
