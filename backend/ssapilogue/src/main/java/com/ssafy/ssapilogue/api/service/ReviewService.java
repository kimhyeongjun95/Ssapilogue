package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateReviewReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindReviewResDto;

import java.util.List;

public interface ReviewService {
    List<FindReviewResDto> findReviews(Long projectId);
    List<String> createReview(String usrEmail, List<CreateReviewReqDto> createReviewReqDtos);
}
