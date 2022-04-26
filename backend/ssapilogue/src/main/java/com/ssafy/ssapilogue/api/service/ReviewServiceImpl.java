package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateReviewReqDto;
import com.ssafy.ssapilogue.core.domain.Review;
import com.ssafy.ssapilogue.core.domain.Survey;
import com.ssafy.ssapilogue.core.domain.SurveyOption;
import com.ssafy.ssapilogue.core.repository.ReviewRepository;
import com.ssafy.ssapilogue.core.repository.SurveyOptionRepository;
import com.ssafy.ssapilogue.core.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService{

    private final SurveyRepository surveyRepository;

    private final SurveyOptionRepository surveyOptionRepository;

    private final ReviewRepository reviewRepository;

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
