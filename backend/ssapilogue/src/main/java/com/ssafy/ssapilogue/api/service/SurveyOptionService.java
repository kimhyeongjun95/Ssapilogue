package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateSurveyOptionReqDto;
import com.ssafy.ssapilogue.core.domain.SurveyOption;

public interface SurveyOptionService {
    SurveyOption createSurveyOption(Long surveyId, CreateSurveyOptionReqDto createSurveyOptionReqDto);
    void deleteSurveyOption(Long surveyOptionId);
}
