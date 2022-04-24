package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateReviewReqDto;
import com.ssafy.ssapilogue.core.domain.Review;
import com.ssafy.ssapilogue.core.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    @Override
    public Long createReview(Long projectId, CreateReviewReqDto createReviewReqDto) {
        Review review = Review.builder()
                .build();

        if (createReviewReqDto.getIndex() != null) {
            review.saveIndex(createReviewReqDto.getIndex());
        } else if (createReviewReqDto.getContent() != null) {
            review.saveContent(createReviewReqDto.getContent());
        }

        Review saveReview = reviewRepository.save(review);

        return saveReview.getId();
    }
}
