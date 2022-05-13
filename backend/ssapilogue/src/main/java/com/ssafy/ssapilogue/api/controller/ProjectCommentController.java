package com.ssafy.ssapilogue.api.controller;

import com.ssafy.ssapilogue.api.dto.request.CreateProjectCommentReqDto;
import com.ssafy.ssapilogue.api.exception.CustomException;
import com.ssafy.ssapilogue.api.exception.ErrorCode;
import com.ssafy.ssapilogue.api.service.JwtTokenProvider;
import com.ssafy.ssapilogue.api.service.ProjectCommentService;
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

@Api(tags = "ProjectComment", value = "프로젝트 댓글 API")
@RestController
@RequestMapping("/project-comment")
@RequiredArgsConstructor
public class ProjectCommentController {

    private final ProjectCommentService projectCommentService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/{projectId}")
    @ApiOperation(value = "댓글 등록", notes = "새로운 댓글을 등록한다.")
    public ResponseEntity<Map<String, Object>> createComment(
            @PathVariable @ApiParam(value = "프로젝트 id", required = true, example = "1") Long projectId,
            @RequestBody @ApiParam(value="프로젝트 댓글 정보", required = true) CreateProjectCommentReqDto createProjectCommentReqDto,
            HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();

        String token = jwtTokenProvider.resolveToken(request);
        if (token == null) throw new CustomException(ErrorCode.NO_TOKEN);

        String userEmail = jwtTokenProvider.getUserEmail(token);
        if (userEmail == null) throw new CustomException(ErrorCode.WRONG_TOKEN);

        Long commentId = projectCommentService.createComment(projectId, createProjectCommentReqDto, userEmail);
        result.put("commentId", commentId);

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제한다.")
    public ResponseEntity<Map<String, Object>> deleteComment(
            @PathVariable @ApiParam(value = "댓글 id", required = true, example = "1") Long commentId) {
        Map<String, Object> result = new HashMap<>();

        projectCommentService.deleteComment(commentId);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }
}
