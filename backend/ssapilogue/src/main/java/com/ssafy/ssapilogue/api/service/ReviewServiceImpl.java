package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateReviewReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindObjectiveReviewResDto;
import com.ssafy.ssapilogue.api.dto.response.FindReviewResDto;
import com.ssafy.ssapilogue.api.dto.response.FindSubjectiveReviewResDto;
import com.ssafy.ssapilogue.core.domain.*;
import com.ssafy.ssapilogue.core.repository.ReviewRepository;
import com.ssafy.ssapilogue.core.repository.SurveyOptionRepository;
import com.ssafy.ssapilogue.core.repository.SurveyRepository;
import com.ssafy.ssapilogue.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

        List<Survey> surveys = surveyRepository.findAllByProjectIdOrderById(projectId);
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
    public List<String> createReview(String userEmail, List<CreateReviewReqDto> createReviewReqDtos) {
        List<String> reviewIds = new ArrayList<>();

        for (CreateReviewReqDto createReviewReqDto : createReviewReqDtos) {
            Survey survey = surveyRepository.findById(createReviewReqDto.getSurveyId())
                    .orElseThrow(() -> new IllegalStateException("존재하지 않는 설문조사입니다."));

            Review review = Review.builder()
                    .userEmail(userEmail)
                    .survey(survey)
                    .build();

            if (createReviewReqDto.getSurveyOptionId() != null) {
                SurveyOption surveyOption = surveyOptionRepository.findById(createReviewReqDto.getSurveyOptionId())
                                .orElseThrow(() -> new IllegalStateException("존재하지 않는 옵션입니다."));
                review.saveSurveyOption(surveyOption);
            } else if (createReviewReqDto.getContent() != null) {
                review.saveContent(createReviewReqDto.getContent());
            }

            Review saveReview = reviewRepository.save(review);
            reviewIds.add(saveReview.getId());
        }

        return reviewIds;
    }
}
