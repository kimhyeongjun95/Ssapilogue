package com.ssafy.ssapilogue.api.controller;

import com.ssafy.ssapilogue.api.dto.request.CreateReviewReqDto;
import com.ssafy.ssapilogue.api.dto.request.CreateReviewsReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindReviewResDto;
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

    @GetMapping("/{projectId}")
    @ApiOperation(value = "리뷰 조회", notes = "리뷰를 전체 조회한다.")
    public ResponseEntity<Map<String, Object>> findReviews(
            @PathVariable @ApiParam(value = "프로젝트 id", required = true, example = "1") Long projectId) {
        Map<String, Object> result = new HashMap<>();

        List<FindReviewResDto> reviewList = reviewService.findReviews(projectId);
        result.put("reviewList", reviewList);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "리뷰 등록", notes = "새로운 리뷰를 등록한다.")
    public ResponseEntity<Map<String, Object>> createReview(
            @RequestBody @ApiParam(value = "리뷰 정보", required = true) CreateReviewsReqDto createReviewsReqDtos,
            HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        String token = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserEmail(token);

        List<String> reviewIds = reviewService.createReview(userEmail, createReviewsReqDtos.getReviews());
        result.put("reviewIds", reviewIds);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.CREATED);
    }
}
