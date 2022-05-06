package com.ssafy.ssapilogue.api.controller;

import com.ssafy.ssapilogue.api.service.UserInfoService;
import com.ssafy.ssapilogue.core.domain.UserInfo;
import com.ssafy.ssapilogue.core.repository.UserInfoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "UserInfo", value = "유저정보 API")
@RestController
@RequestMapping("/user-info")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;
    private final UserInfoRepository userInfoRepository;

    @GetMapping
    @ApiOperation(value = "회원정보 저장", notes = "회원 정보 전체를 DB에 저장한다.")
    public ResponseEntity<Map<String, Object>> getUserInfo() throws ParseException {
        Map<String, Object> result = new HashMap<>();

        userInfoService.getUserInfo();
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @PostMapping()
    @ApiOperation(value = "자모음 분리", notes = "자모음을 분리한다.")
    public ResponseEntity<Map<String, Object>> splitUserInfo() {

        Map<String, Object> result = new HashMap<>();

        userInfoService.splitUserInfo();
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @GetMapping("/search")
    @ApiOperation(value = "멤버 자동완성", notes = "멤버를 검색한다.")
    public ResponseEntity<Map<String, Object>> searchUserInfos(
            @RequestParam @ApiParam(value = "검색어") String keyword) {

        Map<String, Object> result = new HashMap<>();

        List<String> searchList = userInfoService.searchUserInfos(keyword);
        result.put("searchList", searchList);

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }
}
