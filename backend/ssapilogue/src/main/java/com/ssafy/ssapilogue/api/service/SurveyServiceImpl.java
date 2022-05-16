package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateSurveyReqDto;
import com.ssafy.ssapilogue.api.dto.request.DeleteSurveyReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindDefaultSurveyResDto;
import com.ssafy.ssapilogue.api.dto.response.FindSurveyOptionResDto;
import com.ssafy.ssapilogue.api.dto.response.FindSurveyResDto;
import com.ssafy.ssapilogue.api.exception.CustomException;
import com.ssafy.ssapilogue.api.exception.ErrorCode;
import com.ssafy.ssapilogue.core.domain.Review;
import com.ssafy.ssapilogue.core.domain.Survey;
import com.ssafy.ssapilogue.core.domain.SurveyOption;
import com.ssafy.ssapilogue.core.domain.SurveyType;
import com.ssafy.ssapilogue.core.repository.ReviewRepository;
import com.ssafy.ssapilogue.core.repository.SurveyOptionRepository;
import com.ssafy.ssapilogue.core.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;

    private final SurveyOptionRepository surveyOptionRepository;

    private final ReviewRepository reviewRepository;

    private final SurveyOptionService surveyOptionService;

    @Override
    public List<FindSurveyResDto> findSurveys(Long projectId) {
        List<FindSurveyResDto> surveyList = new ArrayList<>();

        List<Survey> surveys = surveyRepository.findAllByProjectId(projectId);
        for (Survey survey : surveys) {
            List<FindSurveyOptionResDto> surveyOptionList = new ArrayList<>();

            List<SurveyOption> surveyOptions = surveyOptionRepository.findAllBySurveyId(survey.getId());
            for (SurveyOption surveyOption : surveyOptions) {
                surveyOptionList.add(new FindSurveyOptionResDto(surveyOption));
            }

            surveyList.add(new FindSurveyResDto(survey, surveyOptionList, ""));
        }

        return surveyList;
    }

    @Override
    public List<String> createSurvey(Long projectId, List<CreateSurveyReqDto> createSurveyReqDtos) {
        List<String> surveyIds = new ArrayList<>();

        for (CreateSurveyReqDto createSurveyReqDto : createSurveyReqDtos) {
            if (createSurveyReqDto.getSurveyId() == null) {
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
                        if (content != null) {
                            SurveyOption surveyOption = surveyOptionService.createSurveyOption(saveSurvey.getId(), content);
                            surveyOptions.add(surveyOption);
                        }
                    }

                    saveSurvey.addSurveyOptions(surveyOptions);
                    surveyRepository.save(saveSurvey);
                }
                surveyIds.add(saveSurvey.getId());
            }
        }
        return surveyIds;
    }

    @Override
    public void deleteSurvey(DeleteSurveyReqDto deleteSurveyReqDto) {
        for (String surveyId : deleteSurveyReqDto.getSurveyIds()) {
            // 객관식인 경우, SurveyOption 삭제
            Survey survey = surveyRepository.findById(surveyId)
                    .orElseThrow(() -> new CustomException(ErrorCode.SURVEY_NOT_FOUND));

            if (survey.getSurveyType() == SurveyType.객관식) {
                for (SurveyOption surveyOption : survey.getSurveyOptions()) {
                    surveyOptionService.deleteSurveyOption(surveyOption.getId());
                }
            }

            // 리뷰 삭제
            List<Review> reviews = reviewRepository.findAllBySurveyOrderByCreatedAtDesc(survey);
            for (Review review : reviews) {
                reviewRepository.deleteById(review.getId());
            }

            surveyRepository.deleteById(surveyId);
        }
    }

    @Override
    public List<FindDefaultSurveyResDto> defaultSurvey(String projectTitle) {
        List<FindDefaultSurveyResDto> defaultSurvey = new ArrayList<>();

        // 완성도
        List<String> option1 = new ArrayList<>();
        option1.add("100%");
        option1.add("80%");
        option1.add("60%");
        option1.add("40%");
        option1.add("20%");
        defaultSurvey.add(new FindDefaultSurveyResDto(projectTitle, "의 완성도는 어느 정도라고 생각하시나요?", "객관식", option1));

        // UI
        List<String> option2 = new ArrayList<>();
        option2.add("그렇다");
        option2.add("그런편이다");
        option2.add("보통이다");
        option2.add("그렇지 않다");
        option2.add("전혀 그렇지 않다");
        defaultSurvey.add(new FindDefaultSurveyResDto(projectTitle, "의 UI(User Interface)에 만족하셨나요?", "객관식", option2));

        // 기능 작동
        defaultSurvey.add(new FindDefaultSurveyResDto(projectTitle, "의 기능은 문제없이 잘 작동한다고 생각하시나요?", "객관식", option2));

        // 사용성
        defaultSurvey.add(new FindDefaultSurveyResDto(projectTitle, "가 정식으로 서비스된다면 계속 사용할 의향이 있으신가요?", "객관식", option2));

        // 자유 의견
        defaultSurvey.add(new FindDefaultSurveyResDto(projectTitle, "에 대해 자유롭게 의견을 남겨주세요!", "주관식", null));

        return defaultSurvey;
    }
}
