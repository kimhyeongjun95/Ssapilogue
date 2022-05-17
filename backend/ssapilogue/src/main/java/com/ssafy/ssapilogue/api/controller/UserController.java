package com.ssafy.ssapilogue.api.controller;

import com.ssafy.ssapilogue.api.config.JwtProperties;
import com.ssafy.ssapilogue.api.dto.request.LoginUserReqDto;
import com.ssafy.ssapilogue.api.dto.request.SignupUserReqDto;
import com.ssafy.ssapilogue.api.dto.request.UpdateUserReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindUserResDto;
import com.ssafy.ssapilogue.api.dto.response.LoginUserResDto;
import com.ssafy.ssapilogue.api.dto.response.SignupUserResDto;
import com.ssafy.ssapilogue.api.exception.CustomException;
import com.ssafy.ssapilogue.api.exception.ErrorCode;
import com.ssafy.ssapilogue.api.service.JwtTokenProvider;
import com.ssafy.ssapilogue.api.service.UserInfoService;
import com.ssafy.ssapilogue.api.service.UserService;
import com.ssafy.ssapilogue.api.util.MediaUtils;
import com.ssafy.ssapilogue.core.domain.User;
import com.ssafy.ssapilogue.core.repository.UserInfoRepository;
import com.ssafy.ssapilogue.core.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "User", value = "유저 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final UserInfoService userInfoService;

    private final UserInfoRepository userInfoRepository;

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${profileImg.path}")
    private String uploadPath;

    @PostMapping
    @ApiOperation(value = "회원가입", notes = "회원가입을 한다.")
    public ResponseEntity<Map<String, Object>> signup(
            @RequestBody @ApiParam(value = "회원가입 유저 정보", required = true) SignupUserReqDto signupUserReqDto) throws Exception {
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        User user = userService.signup(signupUserReqDto);

        httpStatus = HttpStatus.OK;
        result.put("message", "success");

        SignupUserResDto signupUserResDto = new SignupUserResDto(user);
        result.put("userinfo", signupUserResDto);

        String token = jwtTokenProvider.createToken(signupUserResDto.getEmail(), signupUserResDto.getUserIdentity());
        result.put("token", token);

        String refreshToken = jwtTokenProvider.createRefreshToken(signupUserReqDto.getEmail());
        result.put("refresh-token", refreshToken);
        user.changeRefreshToken(refreshToken);
        userRepository.save(user);

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "로그인을 한다.")
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody @ApiParam(value = "로그인 유저 정보", required = true) LoginUserReqDto loginUserReqDto) throws Exception {
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        String encodeMmId = userInfoService.encrypt(loginUserReqDto.getUserId().getBytes());
        if (userInfoRepository.findByUserId(encodeMmId) == null) {
            throw new CustomException(ErrorCode.INVALID_USER);
        }

        User findUser = userRepository.findByEmail(loginUserReqDto.getEmail());
        if (findUser == null) {
            httpStatus = HttpStatus.OK;
            result.put("status", "NO USER");
            return new ResponseEntity<Map<String, Object>>(result, httpStatus);
        }

        User loginUser = userService.login(loginUserReqDto);

        httpStatus = HttpStatus.OK;
        result.put("status", "SUCCESS");

        String token = jwtTokenProvider.createToken(loginUser.getEmail(), loginUser.getUserIdentity());
        result.put("token", token);

        String refreshToken = jwtTokenProvider.createRefreshToken(loginUserReqDto.getEmail());
        result.put("refresh-token", refreshToken);
        loginUser.changeRefreshToken(refreshToken);
        userRepository.save(loginUser);

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }



    @PutMapping
    @ApiOperation(value = "회원정보 수정", notes = "회원정보를 수정한다.")
    public ResponseEntity<Map<String, Object>> updateUser(HttpServletRequest request,
            @RequestBody @ApiParam(value = "회원정보 수정", required = true) UpdateUserReqDto updateUserReqDto) {
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        String token = jwtTokenProvider.resolveToken(request);
        if (token == null) throw new CustomException(ErrorCode.NO_TOKEN);

        String userEmail = jwtTokenProvider.getUserEmail(token);
        if (userEmail == null) throw new CustomException(ErrorCode.WRONG_TOKEN);

        User user = userRepository.findByEmail(userEmail);
        if (user == null) throw new CustomException(ErrorCode.NO_USER);

        userService.updateUser(user, updateUserReqDto);
        httpStatus = HttpStatus.OK;
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @DeleteMapping
    @ApiOperation(value = "회원 삭제", notes = "회원을 삭제한다.")
    public ResponseEntity<Map<String, Object>> deleteUser(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        String token = jwtTokenProvider.resolveToken(request);
        if (token == null) throw new CustomException(ErrorCode.NO_TOKEN);

        String userEmail = jwtTokenProvider.getUserEmail(token);
        if (userEmail == null) throw new CustomException(ErrorCode.WRONG_TOKEN);

        userService.deleteUser(userEmail);
        httpStatus = HttpStatus.OK;
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @GetMapping
    @ApiOperation(value = "회원 단일 조회", notes = "회원을 조회한다.")
    public ResponseEntity<Map<String, Object>> findUserProfile(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        FindUserResDto findUser = null;

        String token = jwtTokenProvider.resolveToken(request);
        if (token == null) throw new CustomException(ErrorCode.NO_TOKEN);

        String userEmail = jwtTokenProvider.getUserEmail(token);
        if (userEmail == null) throw new CustomException(ErrorCode.WRONG_TOKEN);

        findUser = userService.findUserProfile(userEmail);
        httpStatus = HttpStatus.OK;
        result.put("status", "SUCCESS");
        result.put("user", findUser);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @GetMapping("/profile/{username}")
    @ApiOperation(value = "다른 회원 조회", notes = "다른 회원을 조회한다.")
    public ResponseEntity<Map<String, Object>> findOtherProfile(
            HttpServletRequest request, @PathVariable @ApiParam(value = "프로필 조회할 회원 유저네임", required = true) String username) {
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        FindUserResDto findUser = null;

        String token = jwtTokenProvider.resolveToken(request);
        if (token == null) throw new CustomException(ErrorCode.NO_TOKEN);

        String userEmail = jwtTokenProvider.getUserEmail(token);
        if (userEmail == null) throw new CustomException(ErrorCode.WRONG_TOKEN);

        findUser = userService.findOtherUserProfile(username);
        httpStatus = HttpStatus.OK;
        result.put("status", "SUCCESS");
        result.put("user", findUser);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }


    @PostMapping("/image")
    @ApiOperation(value = "프로필 이미지 업로드", notes = "프로필 이미지를 업로드한다.")
    public ResponseEntity<Map<String, Object>> updateImage(
            @RequestPart @ApiParam(value = "이미지 파일", required = true) MultipartFile file, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        String token = jwtTokenProvider.resolveToken(request);
        if (token == null) throw new CustomException(ErrorCode.NO_TOKEN);

        String userEmail = jwtTokenProvider.getUserEmail(token);
        if (userEmail == null) throw new CustomException(ErrorCode.WRONG_TOKEN);

        String imageUrl = userService.updateImage(userEmail, file);
        httpStatus = HttpStatus.OK;
        result.put("imageUrl", imageUrl);

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        if (refreshToken == null) throw new CustomException(ErrorCode.NO_REFRESH_TOKEN);
        String newToken = userService.issueNewToken(refreshToken);
        response.addHeader(JwtProperties.HEADER_STRING, newToken);
    }
}
