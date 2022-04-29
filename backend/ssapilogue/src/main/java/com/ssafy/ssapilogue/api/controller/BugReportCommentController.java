package com.ssafy.ssapilogue.api.controller;

import com.ssafy.ssapilogue.api.dto.request.CreateBugReportCommentReqDto;
import com.ssafy.ssapilogue.api.service.BugReportCommentService;
import com.ssafy.ssapilogue.api.service.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "BugReportComment", value = "버그리포트 댓글 API")
@RestController
@RequestMapping("/bug-comment")
@RequiredArgsConstructor
public class BugReportCommentController {

    private final BugReportCommentService bugReportCommentService;

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/{bugId}")
    @ApiOperation(value = "버그 리포트 댓글 등록", notes = "새로운 버그 리포트 댓글을 등록한다.")
    public ResponseEntity<Map<String, Object>> createBugReportComment(
            @PathVariable @ApiParam(value = "버그 리포트 id", required = true, example = "1") Long bugId,
            @RequestBody @ApiParam(value = "버그 리포트 댓글 정보", required = true) CreateBugReportCommentReqDto createBugReportCommentReqDto,
            HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        String token = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserEmail(token);

        Long bugCoId = bugReportCommentService.createBugReportComment(bugId, userEmail, createBugReportCommentReqDto);
        result.put("bugCoId", bugCoId);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.CREATED);
    }
}
