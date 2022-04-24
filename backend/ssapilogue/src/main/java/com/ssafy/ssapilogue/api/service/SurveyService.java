package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateSurveyReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindSurveyResDto;

import java.util.List;

public interface SurveyService {
    List<FindSurveyResDto> findSurveys(Long projectId);
    Long createSurvey(CreateSurveyReqDto createSurveyReqDto);
    void deleteSurvey(Long surveyId);
}
