package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateSurveyReqDto;
import com.ssafy.ssapilogue.api.dto.request.DeleteSurveyReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindDefaultSurveyResDto;
import com.ssafy.ssapilogue.api.dto.response.FindEditVerSurveyResDto;
import com.ssafy.ssapilogue.api.dto.response.FindSurveyResDto;

import java.util.List;

public interface SurveyService {
    List<FindSurveyResDto> findSurveys(Long projectId);
    List<String> createSurvey(Long projectId, List<CreateSurveyReqDto> createSurveyReqDtos);
    void deleteSurvey(DeleteSurveyReqDto deleteSurveyReqDto);
    List<FindDefaultSurveyResDto> defaultSurvey(String projectTitle);
    List<FindEditVerSurveyResDto> findEditSurveys(Long projectId);
}
