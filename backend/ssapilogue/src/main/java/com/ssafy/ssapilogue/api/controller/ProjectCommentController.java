package com.ssafy.ssapilogue.api.controller;

import com.ssafy.ssapilogue.api.dto.request.CreateCommentReqDto;
import com.ssafy.ssapilogue.api.service.ProjectCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "ProjectComment", value = "프로젝트 댓글 API")
@RestController
@RequestMapping("/project-comment")
@RequiredArgsConstructor
public class ProjectCommentController {

    private final ProjectCommentService projectCommentService;

    @PostMapping
    @ApiOperation(value = "댓글 등록", notes = "새로운 댓글을 등록한다.")
    public ResponseEntity<Map<String, Object>> createComment(
            @RequestBody @ApiParam(value="댓글 정보") CreateCommentReqDto createCommentReqDto,
            @RequestParam @ApiParam(value = "임시 user id", required = true, example = "string") String userId) {

        Map<String, Object> result = new HashMap<>();

        Long commentId = projectCommentService.createComment(createCommentReqDto, userId);
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

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.CREATED);
    }
}
