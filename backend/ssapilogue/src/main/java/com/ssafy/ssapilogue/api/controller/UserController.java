package com.ssafy.ssapilogue.api.controller;

import com.ssafy.ssapilogue.api.dto.request.UserLoginDto;
import com.ssafy.ssapilogue.api.dto.request.UserRequestDto;
import com.ssafy.ssapilogue.api.dto.response.UserSimpleResponseDto;
import com.ssafy.ssapilogue.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody UserRequestDto userDto) {
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        try {
            UserSimpleResponseDto userSimpleResponseDto = userService.signup(userDto);
            result.put("userinfo", userSimpleResponseDto);
            httpStatus = HttpStatus.OK;
            result.put("message", "success");
            System.out.println("success");
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("error");
        }
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserLoginDto userLoginDto) {
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        try {
            userService.login(userLoginDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }
}
