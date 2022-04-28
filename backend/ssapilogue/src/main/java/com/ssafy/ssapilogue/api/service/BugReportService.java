package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateBugReportReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindBugReportDto;

import java.util.List;

public interface BugReportService {
    List<FindBugReportDto> findBugReports(Long projectId);
    Long createBugReport(Long projectId, String userEmail, CreateBugReportReqDto createBugReportReqDto);
}
