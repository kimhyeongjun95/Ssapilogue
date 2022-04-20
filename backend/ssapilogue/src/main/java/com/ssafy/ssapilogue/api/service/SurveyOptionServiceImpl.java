package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateSurveyOptionReqDto;
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
    public SurveyOption createSurveyOption(Long surveyId, CreateSurveyOptionReqDto createSurveyOptionReqDto) {
        SurveyOption surveyOption = SurveyOption.builder()
                .surveyId(surveyId)
                .index(createSurveyOptionReqDto.getIndex())
                .content(createSurveyOptionReqDto.getContent())
                .build();

        SurveyOption saveSurveyOption = surveyOptionRepository.save(surveyOption);

        return saveSurveyOption;
    }

    @Override
    public void deleteSurveyOption(Long surveyOptionId) {
        surveyOptionRepository.deleteById(surveyOptionId);
    }
}
