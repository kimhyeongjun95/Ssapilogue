package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateBugReportReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindBugReportDetailResDto;
import com.ssafy.ssapilogue.api.dto.response.FindBugReportsResDto;

public interface BugReportService {
    FindBugReportsResDto findBugReports(Long projectId);
    Long createBugReport(Long projectId, String userEmail, CreateBugReportReqDto createBugReportReqDto);
    FindBugReportDetailResDto findBugReportDetail(Long bugId);
    void updateBugReport(Long bugId, CreateBugReportReqDto createBugReportReqDto);
    void deleteBugReport(Long bugId);
    Boolean solvedBugReport(Long bugId);
}
