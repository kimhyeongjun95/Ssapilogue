package com.ssafy.ssapilogue.api.controller;

import com.ssafy.ssapilogue.api.dto.response.FindProjectResDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectTitleResDto;
import com.ssafy.ssapilogue.api.service.JwtTokenProvider;
import com.ssafy.ssapilogue.api.service.TechStackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "TechStack", value = "기술스택 API")
@RestController
@RequestMapping("/tech-stack")
@RequiredArgsConstructor
public class TechStackController {

    private final TechStackService techStackService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/search/project")
    @ApiOperation(value = "기술스택으로 프로젝트 검색", notes = "기술스택으로 프로젝트를 검색한다.")
    public ResponseEntity<Map<String, Object>> searchProjectsByTechStack(
            @RequestParam @ApiParam(value = "검색어") String keyword,
            HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();
        List<FindProjectResDto> projectList = null;

        String token = jwtTokenProvider.resolveToken(request);
        if (token == null) {
            projectList = techStackService.searchProjectsByTechStack(keyword, "");
        } else {
            String userEmail = jwtTokenProvider.getUserEmail(token);
            projectList = techStackService.searchProjectsByTechStack(keyword, userEmail);
        }

        result.put("projectList", projectList);

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @GetMapping("/search/specific/project")
    @ApiOperation(value = "정확한 기술스택으로 프로젝트 검색", notes = "정확한 기술스택으로 프로젝트를 검색한다.")
    public ResponseEntity<Map<String, Object>> searchProjectsByTechStackSpecific(
            @RequestParam @ApiParam(value = "검색어") String keyword,
            HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();
        List<FindProjectResDto> projectList = null;

        String token = jwtTokenProvider.resolveToken(request);
        if (token == null) {
            projectList = techStackService.searchProjectsByTechStackSpecific(keyword, "");
        } else {
            String userEmail = jwtTokenProvider.getUserEmail(token);
            projectList = techStackService.searchProjectsByTechStackSpecific(keyword, userEmail);
        }

        result.put("projectList", projectList);

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @GetMapping("/search/title")
    @ApiOperation(value = "기술스택으로 프로젝트 제목 자동완성", notes = "기술스택으로 프로젝트 제목을 검색한다.")
    public ResponseEntity<Map<String, Object>> searchProjectTitlesByTechStack(
            @RequestParam @ApiParam(value = "검색어") String keyword) {

        Map<String, Object> result = new HashMap<>();

        List<FindProjectTitleResDto> searchList = techStackService.searchProjectTitlesByTechStack(keyword);
        result.put("searchList", searchList);

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @GetMapping("/search/specific")
    @ApiOperation(value = "기술스택 자동완성", notes = "기술스택을 검색한다.")
    public ResponseEntity<Map<String, Object>> searchTechStacks(
            @RequestParam @ApiParam(value = "검색어") String keyword) {

        Map<String, Object> result = new HashMap<>();

        List<String> searchList = techStackService.searchTechStacks(keyword);
        result.put("searchList", searchList);

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }
}
