package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateBugReportReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindBugReportCommentResDto;
import com.ssafy.ssapilogue.api.dto.response.FindBugReportDetailResDto;
import com.ssafy.ssapilogue.api.dto.response.FindBugReportResDto;
import com.ssafy.ssapilogue.core.domain.BugReport;
import com.ssafy.ssapilogue.core.domain.BugReportComment;
import com.ssafy.ssapilogue.core.domain.Project;
import com.ssafy.ssapilogue.core.domain.User;
import com.ssafy.ssapilogue.core.repository.BugReportCommentRepository;
import com.ssafy.ssapilogue.core.repository.BugReportRepository;
import com.ssafy.ssapilogue.core.repository.ProjectRepository;
import com.ssafy.ssapilogue.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class BugReportServiceImpl implements BugReportService{

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    private final BugReportRepository bugReportRepository;

    private final BugReportCommentRepository bugReportCommentRepository;

    @Override
    public List<FindBugReportResDto> findBugReports(Long projectId) {
        List<FindBugReportResDto> findBugReportResDtos = new ArrayList<>();

        List<BugReport> bugReports = bugReportRepository.findAllByProjectIdOrderById(projectId);
        for (BugReport bugReport : bugReports) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String createAt = bugReport.getCreatedAt().format(formatter);

            findBugReportResDtos.add(new FindBugReportResDto(bugReport, createAt));
        }

        return findBugReportResDtos;
    }

    @Override
    public Long createBugReport(Long projectId, String userEmail, CreateBugReportReqDto createBugReportReqDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 프로젝트입니다."));

        User user = userRepository.findByEmail(userEmail);

        BugReport bugReport = BugReport.builder()
                .project(project)
                .user(user)
                .title(createBugReportReqDto.getTitle())
                .content(createBugReportReqDto.getContent())
                .is_solved(false)
                .build();

        BugReport saveBugReport = bugReportRepository.save(bugReport);
        return saveBugReport.getId();
    }

    @Override
    public FindBugReportDetailResDto findBugReportDetail(Long bugId) {
        BugReport bugReport = bugReportRepository.findById(bugId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 버그 리포트입니다."));

        User user = bugReport.getUser();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String createdAt = bugReport.getCreatedAt().format(formatter);

        List<FindBugReportCommentResDto> comments = new ArrayList<>();
        List<BugReportComment> findComments = bugReportCommentRepository.findAllByBugReportIdOrderById(bugId);
        for (BugReportComment bugReportComment : findComments) {
            User commentUser = bugReportComment.getUser();
            String commentCreatedAt = bugReportComment.getCreatedAt().format(formatter);

            comments.add(new FindBugReportCommentResDto(bugReportComment, commentUser, commentCreatedAt));
        }

        return new FindBugReportDetailResDto(bugReport, user, createdAt, comments);
    }

    @Override
    public void updateBugReport(Long bugId, CreateBugReportReqDto createBugReportReqDto) {
        BugReport bugReport = bugReportRepository.findById(bugId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 버그 리포트입니다."));

        bugReport.update(createBugReportReqDto);
    }

    @Override
    public void deleteBugReport(Long bugId) {
        bugReportRepository.deleteById(bugId);
    }
}
