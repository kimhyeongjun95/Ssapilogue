package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateReviewReqDto;

public interface ReviewService {
    Long createReview(Long projectId, CreateReviewReqDto createReviewReqDto);
}
