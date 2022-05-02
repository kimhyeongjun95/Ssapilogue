package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateBugReportReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindBugReportDetailResDto;
import com.ssafy.ssapilogue.api.dto.response.FindBugReportResDto;

import java.util.List;

public interface BugReportService {
    List<FindBugReportResDto> findBugReports(Long projectId);
    Long createBugReport(Long projectId, String userEmail, CreateBugReportReqDto createBugReportReqDto);
    FindBugReportDetailResDto findBugReportDetail(Long bugId);
}
