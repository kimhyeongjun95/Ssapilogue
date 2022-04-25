package com.ssafy.ssapilogue.api.controller;

import com.ssafy.ssapilogue.api.dto.request.LoginUserReqDto;
import com.ssafy.ssapilogue.api.dto.request.SignupUserReqDto;
import com.ssafy.ssapilogue.api.dto.request.UpdateUserReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindUserResDto;
import com.ssafy.ssapilogue.api.dto.response.SignupUserResDto;
import com.ssafy.ssapilogue.api.service.JwtTokenProvider;
import com.ssafy.ssapilogue.api.service.UserService;
import com.ssafy.ssapilogue.api.util.MediaUtils;
import com.ssafy.ssapilogue.core.domain.User;
import com.ssafy.ssapilogue.core.repository.UserInfoRepository;
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
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "User", value = "유저 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final UserInfoRepository userInfoRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${profileImg.path}")
    private String uploadPath;

    @PostMapping
    @ApiOperation(value = "회원가입", notes = "회원가입을 한다.")
    public ResponseEntity<Map<String, Object>> signup(
            @RequestBody @ApiParam(value = "회원가입 유저 정보", required = true) SignupUserReqDto signupUserReqDto) throws Exception {
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        try {
            SignupUserResDto signupUserResDto = userService.signup(signupUserReqDto);
            result.put("userinfo", signupUserResDto);
            httpStatus = HttpStatus.OK;
            result.put("message", "success");
            System.out.println("success");
            String token = jwtTokenProvider.createToken(signupUserResDto.getEmail(), signupUserResDto.getUserIdentity());
            result.put("token", token);
        }
        catch(Exception e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("message", "error");
            System.out.println("error");
        }
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "로그인을 한다.")
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody @ApiParam(value = "로그인 유저 정보", required = true) LoginUserReqDto loginUserReqDto) {
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        if (userInfoRepository.findByUserId(loginUserReqDto.getUserId()) == null) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            result.put("message", "ssapilogue를 이용할 수 없습니다.");
            return new ResponseEntity<Map<String, Object>>(result, httpStatus);
        }
        try {
            User loginUser = userService.login(loginUserReqDto);
            httpStatus = HttpStatus.OK;
            result.put("status", "SUCCESS");
            String token = jwtTokenProvider.createToken(loginUser.getEmail(), loginUser.getUserIdentity());
            result.put("token", token);
        } catch (NullPointerException e) {
            httpStatus = HttpStatus.OK;
            result.put("status", "NO USER");
        } catch (RuntimeException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SEVER ERROR");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @PutMapping
    @ApiOperation(value = "회원정보 수정", notes = "회원정보를 수정한다.")
    public ResponseEntity<Map<String, Object>> updateUser(
            @RequestBody @ApiParam(value = "회원정보 수정", required = true) UpdateUserReqDto updateUserReqDto) {
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        try {
            userService.updateUser(updateUserReqDto);
            httpStatus = HttpStatus.OK;
            result.put("status", "SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @DeleteMapping
    @ApiOperation(value = "회원 삭제", notes = "회원을 삭제한다.")
    public ResponseEntity<Map<String, Object>> deleteUser(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        String token = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserEmail(token);
        try {
            userService.deleteUser(userEmail);
            httpStatus = HttpStatus.OK;
            result.put("status", "SUCCESS");
        } catch (Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @GetMapping
    @ApiOperation(value = "회원 단일 조회", notes = "회원을 조회한다.")
    public ResponseEntity<Map<String, Object>> findUserProfile(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        FindUserResDto findUser = null;
        String token = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserEmail(token);
        try {
            findUser = userService.findUserProfile(userEmail);
            httpStatus = HttpStatus.OK;
            result.put("status", "SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER_ERROR");
        }
        result.put("user", findUser);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @PostMapping("/image")
    @ApiOperation(value = "프로필 이미지 변경", notes = "회원의 프로필 이미지를 변경한다.")
    public ResponseEntity<Map<String, Object>> updateImage(
            @RequestPart @ApiParam(value = "이미지 파일", required = true) MultipartFile file, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        String token = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserEmail(token);

        try {
            userService.updateImage(userEmail, file);
            httpStatus = HttpStatus.OK;
            result.put("status", "SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @GetMapping(value = "/image")
    @ApiOperation(value = "프로필 이미지 불러오기", notes = "회원의 프로필 이미지를 불러온다.")
    public ResponseEntity<byte[]> displayImage(HttpServletRequest request) throws Exception {

        InputStream in = null;
        ResponseEntity<byte[]> entity = null;
        String token = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserEmail(token);
        FindUserResDto userProfile = userService.findUserProfile(userEmail);
        String imageName = userProfile.getImage();
        try {
            String formatName = imageName.substring(imageName.lastIndexOf(".")+1);
            MediaType mType = MediaUtils.getMediaType(formatName);
            HttpHeaders headers = new HttpHeaders();
            in = new FileInputStream(uploadPath + imageName);

            if (mType != null) {
                headers.setContentType(mType);
            } else {
                imageName = imageName.substring(imageName.indexOf("_")+1);
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.add("Content-Disposition", "attachment; filename=\"" + new String(imageName.getBytes(StandardCharsets.UTF_8), "ISO-8859-1") + "\"");
            }
            entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
        } finally {
            in.close();
        }
        return entity;
    }
}
