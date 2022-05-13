package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateProjectCommentReqDto;

public interface ProjectCommentService {

    Long createComment(Long projectId, CreateProjectCommentReqDto createProjectCommentReqDto, String userEmail);
    void deleteComment(Long commentId);
}
