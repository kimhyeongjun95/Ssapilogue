package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateReviewReqDto;

public interface ReviewService {
    String createReview(Long projectId, CreateReviewReqDto createReviewReqDto);
}
