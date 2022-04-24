package com.ssafy.ssapilogue.api.controller;

import com.ssafy.ssapilogue.api.dto.request.CreateReviewReqDto;
import com.ssafy.ssapilogue.api.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "Review", value = "리뷰 API")
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{projectId}")
    @ApiOperation(value = "리뷰 등록", notes = "새로운 리뷰를 등록한다.")
    public ResponseEntity<Map<String, Object>> createReview(
            @PathVariable @ApiParam(value = "프로젝트 id", required = true, example = "1") Long projectId,
            @RequestBody @ApiParam(value = "리뷰 정보", required = true) CreateReviewReqDto createReviewReqDto) {
        Map<String, Object> result = new HashMap<>();

        String reviewId = reviewService.createReview(projectId, createReviewReqDto);
        result.put("reviewId", reviewId);
        result.put("status", "SUCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.CREATED);
    }
}
