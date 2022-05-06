package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.response.FindObjectiveReviewResDto;
import com.ssafy.ssapilogue.api.dto.response.FindReviewResDto;
import com.ssafy.ssapilogue.api.dto.response.FindSubjectiveReviewResDto;
import com.ssafy.ssapilogue.api.dto.response.FindSurveyResDto;
import com.ssafy.ssapilogue.api.exception.CustomException;
import com.ssafy.ssapilogue.api.exception.ErrorCode;
import com.ssafy.ssapilogue.core.domain.*;
import com.ssafy.ssapilogue.core.repository.ReviewRepository;
import com.ssafy.ssapilogue.core.repository.SurveyOptionRepository;
import com.ssafy.ssapilogue.core.repository.SurveyRepository;
import com.ssafy.ssapilogue.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService{

    private final SurveyRepository surveyRepository;

    private final SurveyOptionRepository surveyOptionRepository;

    private final UserRepository userRepository;

    private final ReviewRepository reviewRepository;

    @Override
    public List<FindReviewResDto> findReviews(Long projectId) {
        List<FindReviewResDto> findReviewResDtos = new ArrayList<>();
        Integer index = 1;

        List<Survey> surveys = surveyRepository.findAllByProjectId(projectId);
        for (Survey survey : surveys) {
            String surveyTitle = survey.getTitle();

            List<Review> reviews = reviewRepository.findAllBySurvey(survey);
            Integer totalCount = reviews.size();

            List<FindObjectiveReviewResDto> objectiveReviews = new ArrayList<>();
            List<FindSubjectiveReviewResDto> subjectiveReviews = new ArrayList<>();
            if (survey.getSurveyType() == SurveyType.객관식) {
                List<SurveyOption> surveyOptions = survey.getSurveyOptions();
                for (SurveyOption surveyOption : surveyOptions) {
                    Integer count = reviewRepository.countAllBySurveyOption(surveyOption);
                    objectiveReviews.add(new FindObjectiveReviewResDto(surveyOption.getContent(), count));
                }
            } else {
                for (Review review : reviews) {
                    User user = userRepository.findByEmail(review.getUserEmail());

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    String createdAt = review.getCreatedAt().format(formatter);

                    subjectiveReviews.add(new FindSubjectiveReviewResDto(review, user, createdAt));
                }
            }

            findReviewResDtos.add(new FindReviewResDto(index, surveyTitle, totalCount, objectiveReviews, subjectiveReviews));
            index ++;
        }
        return findReviewResDtos;
    }

    @Override
    public List<String> createReview(String userEmail, List<FindSurveyResDto> reviews) {
        List<String> reviewIds = new ArrayList<>();

        for (FindSurveyResDto findSurveyResDto : reviews) {
            Survey survey = surveyRepository.findById(findSurveyResDto.getSurveyId())
                    .orElseThrow(() -> new CustomException(ErrorCode.SURVEY_NOT_FOUND));

            Review review = Review.builder()
                    .userEmail(userEmail)
                    .survey(survey)
                    .build();

            if (survey.getSurveyType() == SurveyType.객관식) {
                SurveyOption surveyOption = surveyOptionRepository.findById(findSurveyResDto.getAnswer())
                        .orElseThrow(() -> new CustomException(ErrorCode.SURVEY_OPTION_NOT_FOUND));
                review.saveSurveyOption(surveyOption);
            } else if (survey.getSurveyType() == SurveyType.주관식) {
                review.saveContent(findSurveyResDto.getAnswer());
            }

            Review saveReview = reviewRepository.save(review);
            reviewIds.add(saveReview.getId());
        }

        return reviewIds;
    }
}
