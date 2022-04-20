package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateSurveyOptionReqDto;
import com.ssafy.ssapilogue.core.domain.Survey;
import com.ssafy.ssapilogue.core.domain.SurveyOption;
import com.ssafy.ssapilogue.core.repository.SurveyOptionRepository;
import com.ssafy.ssapilogue.core.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class SurveyOptionServiceImpl implements SurveyOptionService{

    private final SurveyRepository surveyRepository;
    private final SurveyOptionRepository surveyOptionRepository;

    @Override
    public Long createSurveyOption(Long surveyId, CreateSurveyOptionReqDto createSurveyOptionReqDto) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 설문조사입니다."));

        SurveyOption surveyOption = SurveyOption.builder()
                .survey(survey)
                .index(createSurveyOptionReqDto.getIndex())
                .content(createSurveyOptionReqDto.getContent())
                .build();

        SurveyOption saveSurveyOption = surveyOptionRepository.save(surveyOption);

        return saveSurveyOption.getId();
    }

    @Override
    public void deleteSurveyOption(Long surveyOptionId) {
        surveyOptionRepository.deleteById(surveyOptionId);
    }
}
