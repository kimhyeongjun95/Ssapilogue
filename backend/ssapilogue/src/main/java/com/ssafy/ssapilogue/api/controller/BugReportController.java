package com.ssafy.ssapilogue.api.controller;

import com.ssafy.ssapilogue.api.dto.request.CreateBugReportReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindBugReportDetailResDto;
import com.ssafy.ssapilogue.api.dto.response.FindBugReportsResDto;
import com.ssafy.ssapilogue.api.service.BugReportService;
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

@Api(tags = "BugReport", value = "버그리포트 API")
@RestController
@RequestMapping("/bug")
@RequiredArgsConstructor
public class BugReportController {

    private final BugReportService bugReportService;

    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/{projectId}")
    @ApiOperation(value = "버그 리포트 전체 조회", notes = "전체 버그 리포트를 조회한다.")
    public ResponseEntity<Map<String, Object>> findBugReports(
            @PathVariable @ApiParam(value = "프로젝트 id", required = true, example = "1") Long projectId){
        Map<String, Object> result = new HashMap<>();

        FindBugReportsResDto bugList = bugReportService.findBugReports(projectId);
        result.put("bugList", bugList);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @PostMapping("/{projectId}")
    @ApiOperation(value = "버그 리포트 등록", notes = "새로운 버그 리포트를 등록한다.")
    public ResponseEntity<Map<String, Object>> createBugReport(
            @PathVariable @ApiParam(value = "프로젝트 id", required = true, example = "1") Long projectId,
            @RequestBody @ApiParam(value = "버그 리포트 정보", required = true) CreateBugReportReqDto createBugReportReqDto,
            HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        String token = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserEmail(token);

        Long bugId = bugReportService.createBugReport(projectId, userEmail, createBugReportReqDto);
        result.put("bugId", bugId);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.CREATED);
    }

    @GetMapping("/detail/{bugId}")
    @ApiOperation(value = "버그 리포트 상세 조회", notes = "버그 리포트를 조회한다.")
    public ResponseEntity<Map<String, Object>> findBugReportDetail(
            @PathVariable @ApiParam(value = "버그 리포트 id", required = true, example = "1") Long bugId) {
        Map<String, Object> result = new HashMap<>();

        FindBugReportDetailResDto bugReport = bugReportService.findBugReportDetail(bugId);
        result.put("bugReport", bugReport);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @PutMapping("/{bugId}")
    @ApiOperation(value = "버그 리포트 수정", notes = "버그 리포트를 수정한다.")
    public ResponseEntity<Map<String, Object>> updateBugReport(
            @PathVariable @ApiParam(value = "버그 리포트 id", required = true, example = "1") Long bugId,
            @RequestBody @ApiParam(value = "버그 리포트 정보", required = true) CreateBugReportReqDto createBugReportReqDto) {
        Map<String, Object> result = new HashMap<>();

        bugReportService.updateBugReport(bugId, createBugReportReqDto);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{bugId}")
    @ApiOperation(value = "버그 리포트 삭제", notes = "버그 리포트를 삭제한다.")
    public ResponseEntity<Map<String, Object>> deleteBugReport(
            @PathVariable @ApiParam(value = "버그 리포트 id", required = true, example = "1") Long bugId) {
        Map<String, Object> result = new HashMap<>();

        bugReportService.deleteBugReport(bugId);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @PostMapping("solved/{bugId}")
    @ApiOperation(value = "버그 리포트 해결/미해결 변경", notes = "버그 리포트의 해결 상태를 변경한다.")
    public ResponseEntity<Map<String, Object>> solvedBugReport(
            @PathVariable @ApiParam(value = "버그 리포트 id", required = true, example = "1") Long bugId) {
        Map<String, Object> result = new HashMap<>();

        Boolean isSolved = bugReportService.solvedBugReport(bugId);
        result.put("isSolved", isSolved);
        result.put("status", "SUCCESS");

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.CREATED);
    }
}
