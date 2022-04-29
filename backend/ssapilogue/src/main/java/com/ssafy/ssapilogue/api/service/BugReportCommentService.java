package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateBugReportCommentReqDto;

public interface BugReportCommentService {
    Long createBugReportComment(Long bugId, String userEmail, CreateBugReportCommentReqDto createBugReportCommentReqDto);
}
