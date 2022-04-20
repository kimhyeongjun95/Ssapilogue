package com.ssafy.ssapilogue.api.controller;

import com.ssafy.ssapilogue.api.dto.request.CreateSurveyReqDto;
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

    @GetMapping("/{surveyId}")
    @ApiOperation(value = "설문조사 조회", notes = "설문조사를 조회한다.")
    public ResponseEntity<Map<String, Object>> findSurvey(
            @PathVariable @ApiParam(value = "설문조사 id", required = true, example = "1") Long surveyId) {
        Map<String, Object> result = new HashMap<>();

        List<FindSurveyResDto> surveyList = surveyService.findSurvey(surveyId);
        result.put("surveyList", surveyList);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "설문조사 등록", notes = "새로운 설문조사를 등록한다.")
    public ResponseEntity<Map<String, Object>> createSurvey(
            @RequestBody @ApiParam(value = "설문조사 정보", required = true) CreateSurveyReqDto createSurveyReqDto) {
        Map<String, Object> result = new HashMap<>();

        Long surveyId = surveyService.createSurvey(createSurveyReqDto);
        result.put("surveyId", surveyId);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.CREATED);
    }
}
