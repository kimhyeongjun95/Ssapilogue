package com.ssafy.ssapilogue.api.controller;

import com.ssafy.ssapilogue.api.dto.request.CreateSurveyOptionReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindSurveyOptionResDto;
import com.ssafy.ssapilogue.api.service.SurveyOptionService;
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

@Api(tags = "SurveyOption", value = "설문조사 옵션 API")
@RestController
@RequestMapping("/survey-option")
@RequiredArgsConstructor
public class SurveyOptionController {

    private final SurveyOptionService surveyOptionService;

    @GetMapping("/{surveyId}")
    @ApiOperation(value = "설문조사 옵션 조회", notes = "설문조사 id에 맞는 옵션을 조회한다.")
    public ResponseEntity<Map<String, Object>> findSurveyOption(
            @PathVariable @ApiParam(value = "설문조사 id", required = true, example = "1") Long surveyId) {
        Map<String, Object> result = new HashMap<>();

        List<FindSurveyOptionResDto> surveyOptionList = surveyOptionService.findSurveyOption(surveyId);
        result.put("surveyOptionList", surveyOptionList);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "설문조사 옵션 등록", notes = "설문조사에 새로운 옵션을 등록한다.")
    public ResponseEntity<Map<String, Object>> createSurveyOption(
            @RequestBody @ApiParam(value = "설문조사 옵션 정보", required = true) CreateSurveyOptionReqDto createSurveyOptionReqDto) {
        Map<String, Object> result = new HashMap<>();

        Long surveyOptionId = surveyOptionService.createSurveyOption(createSurveyOptionReqDto);
        result.put("surveyOptionId", surveyOptionId);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/{surveyOptionId}")
    @ApiOperation(value = "설문조사 옵션 삭제", notes = "설문조사 옵션을 삭제한다.")
    public ResponseEntity<Map<String, Object>> deleteSurveyOption(
            @PathVariable @ApiParam(value = "설문조사옵션 id", required = true, example = "1") Long surveyOptionId) {
        Map<String, Object> result = new HashMap<>();

        surveyOptionService.deleteSurveyOption(surveyOptionId);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }
}
