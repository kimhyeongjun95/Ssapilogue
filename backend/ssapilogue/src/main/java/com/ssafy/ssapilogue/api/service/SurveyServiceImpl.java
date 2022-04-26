package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateSurveyReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindSurveyResDto;
import com.ssafy.ssapilogue.core.domain.Survey;
import com.ssafy.ssapilogue.core.domain.SurveyOption;
import com.ssafy.ssapilogue.core.domain.SurveyType;
import com.ssafy.ssapilogue.core.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;

    private final SurveyOptionService surveyOptionService;

    @Override
    public List<FindSurveyResDto> findSurveys(Long projectId) {
        List<Survey> surveys = surveyRepository.findAllByProjectIdOrderById(projectId);
        return surveys.stream()
                .map(FindSurveyResDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> createSurvey(Long projectId, List<CreateSurveyReqDto> createSurveyReqDtos) {
        List<String> surveyIds = new ArrayList<>();

        for (CreateSurveyReqDto createSurveyReqDto : createSurveyReqDtos) {
            Survey survey = Survey.builder()
                    .projectId(projectId)
                    .title(createSurveyReqDto.getTitle())
                    .surveyType(SurveyType.valueOf(createSurveyReqDto.getSurveyType()))
                    .build();

            Survey saveSurvey = surveyRepository.save(survey);

            // 객관식인 경우, SurveyOption 추가
            if (saveSurvey.getSurveyType() == SurveyType.객관식) {
                List<SurveyOption> surveyOptions = new ArrayList<>();

                for (String content : createSurveyReqDto.getSurveyOptions()) {
                    SurveyOption surveyOption = surveyOptionService.createSurveyOption(saveSurvey.getId(), content);
                    surveyOptions.add(surveyOption);
                }

                saveSurvey.addSurveyOptions(surveyOptions);
                surveyRepository.save(saveSurvey);
            }
            surveyIds.add(saveSurvey.getId());
        }
        return surveyIds;
    }

    @Override
    public void deleteSurvey(String surveyId) {
        // 객관식인 경우, SurveyOption 삭제
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 설문조사입니다."));

        if (survey.getSurveyType() == SurveyType.객관식) {
            for (SurveyOption surveyOption : survey.getSurveyOptions()) {
                surveyOptionService.deleteSurveyOption(surveyOption.getId());
            }
        }

        surveyRepository.deleteById(surveyId);
    }
}
