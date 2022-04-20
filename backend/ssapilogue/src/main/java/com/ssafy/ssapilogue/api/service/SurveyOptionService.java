package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateSurveyOptionReqDto;

public interface SurveyOptionService {
    Long createSurveyOption(Long surveyId, CreateSurveyOptionReqDto createSurveyOptionReqDto);
    void deleteSurveyOption(Long surveyOptionId);
}
