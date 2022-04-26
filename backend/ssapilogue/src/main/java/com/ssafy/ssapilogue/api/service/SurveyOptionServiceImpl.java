package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.core.domain.SurveyOption;
import com.ssafy.ssapilogue.core.repository.SurveyOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class SurveyOptionServiceImpl implements SurveyOptionService{

    private final SurveyOptionRepository surveyOptionRepository;

    @Override
    public SurveyOption createSurveyOption(String surveyId, String content) {
        SurveyOption surveyOption = SurveyOption.builder()
                .surveyId(surveyId)
                .content(content)
                .build();

        SurveyOption saveSurveyOption = surveyOptionRepository.save(surveyOption);

        return saveSurveyOption;
    }

    @Override
    public void deleteSurveyOption(String surveyOptionId) {
        surveyOptionRepository.deleteById(surveyOptionId);
    }
}
