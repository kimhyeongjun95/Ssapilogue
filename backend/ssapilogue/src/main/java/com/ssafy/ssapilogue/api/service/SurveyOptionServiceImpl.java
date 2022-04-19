package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateSurveyOptionReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindSurveyOptionResDto;
import com.ssafy.ssapilogue.core.domain.Survey;
import com.ssafy.ssapilogue.core.domain.SurveyOption;
import com.ssafy.ssapilogue.core.repository.SurveyOptionRepository;
import com.ssafy.ssapilogue.core.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class SurveyOptionServiceImpl implements SurveyOptionService{

    private final SurveyRepository surveyRepository;
    private final SurveyOptionRepository surveyOptionRepository;

    @Override
    public List<FindSurveyOptionResDto> findSurveyOption(Long surveyId) {
        List<SurveyOption> surveyOptions = surveyOptionRepository.findAllBySurveyIdOrderByIndex(surveyId);
        return surveyOptions.stream()
                .map(FindSurveyOptionResDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Long createSurveyOption(CreateSurveyOptionReqDto createSurveyOptionReqDto) {
        Survey survey = surveyRepository.findById(createSurveyOptionReqDto.getSurveyId())
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
