package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.core.domain.SurveyOption;

public interface SurveyOptionService {
    SurveyOption createSurveyOption(String surveyId, String content);
    void deleteSurveyOption(String surveyOptionId);
}
