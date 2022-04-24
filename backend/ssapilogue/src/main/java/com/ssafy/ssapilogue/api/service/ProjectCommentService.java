package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateCommentReqDto;

public interface ProjectCommentService {

    Long createComment(CreateCommentReqDto createCommentReqDto, String userId);
    void deleteComment(Long commentId);
}
