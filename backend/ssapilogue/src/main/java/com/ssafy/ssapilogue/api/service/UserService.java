package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.LoginUserReqDto;
import com.ssafy.ssapilogue.api.dto.request.SignupUserReqDto;
import com.ssafy.ssapilogue.api.dto.request.UpdateUserReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindUserResDto;
import com.ssafy.ssapilogue.api.dto.response.LoginUserResDto;
import com.ssafy.ssapilogue.api.dto.response.SignupUserResDto;
import com.ssafy.ssapilogue.core.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;

public interface UserService {

    User signup(SignupUserReqDto signupUserReqDto);

    User login(LoginUserReqDto loginUserReqDto) throws Exception;

    void updateUser(User user, UpdateUserReqDto updateUserReqDto);

    void deleteUser(String userId);

    FindUserResDto findUserProfile(String email);

    String updateImage(String email, MultipartFile multipartFile);

    String issueNewToken(String refreshToken) throws Exception;

}
