package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateSurveyReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindSurveyResDto;

import java.util.List;

public interface SurveyService {
    List<FindSurveyResDto> findSurveys(Long projectId);
    List<String> createSurvey(Long projectId, List<CreateSurveyReqDto> createSurveyReqDtos);
    void deleteSurvey(String surveyId);
}
