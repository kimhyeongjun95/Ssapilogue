package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateSurveyReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindSurveyResDto;

import java.util.List;

public interface SurveyService {
    List<FindSurveyResDto> findSurvey(Long surveyId);
    Long createSurvey(CreateSurveyReqDto createSurveyReqDto);
}
