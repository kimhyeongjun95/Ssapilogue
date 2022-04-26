package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateReviewReqDto;

import java.util.List;

public interface ReviewService {
    List<String> createReview(String usrEmail, List<CreateReviewReqDto> createReviewReqDtos);
}
