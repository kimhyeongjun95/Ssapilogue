package com.ssafy.ssapilogue.api.controller;

import com.ssafy.ssapilogue.api.dto.request.CreateSurveyReqDto;
import com.ssafy.ssapilogue.api.dto.request.CreateSurveysReqDto;
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

    @DeleteMapping("/{surveyId}")
    @ApiOperation(value = "설문조사 문항 삭제", notes = "설문조사 문항을 삭제한다.")
    public ResponseEntity<Map<String, Object>> deleteSurvey(
            @PathVariable @ApiParam(value = "설문조사 id", required = true, example = "626751b5e139e25c17d1ec8a") String surveyId) {
        Map<String, Object> result = new HashMap<>();

        surveyService.deleteSurvey(surveyId);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }
}
