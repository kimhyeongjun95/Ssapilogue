package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateSurveyOptionReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindSurveyOptionResDto;

import java.util.List;

public interface SurveyOptionService {
    List<FindSurveyOptionResDto> findSurveyOption(Long surveyId);
    Long createSurveyOption(CreateSurveyOptionReqDto createSurveyOptionReqDto);
    void deleteSurveyOption(Long surveyOptionId);
}
