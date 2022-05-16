package com.ssafy.ssapilogue.api.controller;

import com.ssafy.ssapilogue.api.dto.request.CreateSurveysReqDto;
import com.ssafy.ssapilogue.api.dto.request.DeleteSurveyReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindDefaultSurveyResDto;
import com.ssafy.ssapilogue.api.dto.response.FindSurveyResDto;
import com.ssafy.ssapilogue.api.service.SurveyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "Survey", value = "설문조사 API")
@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @GetMapping("/{projectId}")
    @ApiOperation(value = "설문조사 문항 전체 조회", notes = "설문조사 문항을 전체 조회한다.")
    public ResponseEntity<Map<String, Object>> findSurveys(
            @PathVariable @ApiParam(value = "프로젝트 id", required = true, example = "1") Long projectId) {
        Map<String, Object> result = new HashMap<>();

        List<FindSurveyResDto> surveyList = surveyService.findSurveys(projectId);
        result.put("surveyList", surveyList);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @PostMapping("/{projectId}")
    @ApiOperation(value = "설문조사 문항 등록", notes = "새로운 설문조사 문항을 등록한다.")
    public ResponseEntity<Map<String, Object>> createSurvey(
            @PathVariable @ApiParam(value = "프로젝트 id", required = true, example = "1") Long projectId,
            @RequestBody @ApiParam(value = "설문조사 정보", required = true) CreateSurveysReqDto createSurveysReqDto) {
        Map<String, Object> result = new HashMap<>();

        List<String> surveyIds = surveyService.createSurvey(projectId, createSurveysReqDto.getCreateSurveyReqDtos());
        result.put("surveyIds", surveyIds);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.CREATED);
    }

    @DeleteMapping
    @ApiOperation(value = "설문조사 문항 삭제", notes = "설문조사 문항을 삭제한다.")
    public ResponseEntity<Map<String, Object>> deleteSurvey(
            @RequestBody @ApiParam(value = "설문조사 id 리스트", required = true) DeleteSurveyReqDto deleteSurveyReqDto) {
        Map<String, Object> result = new HashMap<>();

        surveyService.deleteSurvey(deleteSurveyReqDto);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @GetMapping("/default/{projectTitle}")
    @ApiOperation(value = "기본 설문조사 문항 조회", notes = "기본 설문조사 문항을 조회한다.")
    public ResponseEntity<Map<String, Object>> defaultSurvey(
            @PathVariable @ApiParam(value = "프로젝트 제목", required = true, example = "싸필로그") String projectTitle) {
        Map<String, Object> result = new HashMap<>();

        List<FindDefaultSurveyResDto> defaultSurvey = surveyService.defaultSurvey(projectTitle);
        result.put("defaultSurvey", defaultSurvey);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }
}
