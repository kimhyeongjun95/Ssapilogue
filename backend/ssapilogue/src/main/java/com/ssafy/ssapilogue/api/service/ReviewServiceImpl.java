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
    public String createReview(Long projectId, CreateReviewReqDto createReviewReqDto) {
        Review review = new Review();

        if (createReviewReqDto.getIndex() != null) {
            review = Review.builder()
                    .index(createReviewReqDto.getIndex())
                    .build();
        } else if (createReviewReqDto.getContent() != null) {
            review = Review.builder()
                    .content(createReviewReqDto.getContent())
                    .build();
        }

        Review saveReview = reviewRepository.save(review);

        return saveReview.getId();
    }
}
