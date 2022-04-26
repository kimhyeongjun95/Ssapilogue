package com.ssafy.ssapilogue.api.controller;

import com.ssafy.ssapilogue.api.dto.request.CreateReviewReqDto;
import com.ssafy.ssapilogue.api.service.JwtTokenProvider;
import com.ssafy.ssapilogue.api.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "Review", value = "리뷰 API")
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    @ApiOperation(value = "리뷰 등록", notes = "새로운 리뷰를 등록한다.")
    public ResponseEntity<Map<String, Object>> createReview(
            @RequestBody @ApiParam(value = "리뷰 정보", required = true) List<CreateReviewReqDto> createReviewReqDtos,
            HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        String token = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserEmail(token);

        List<String> reviewIds = reviewService.createReview(userEmail, createReviewReqDtos);
        result.put("reviewId", reviewIds);
        result.put("status", "SUCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.CREATED);
    }
}
