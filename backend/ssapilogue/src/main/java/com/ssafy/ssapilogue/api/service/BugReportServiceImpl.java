package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateBugReportReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindBugReportDto;
import com.ssafy.ssapilogue.core.domain.BugReport;
import com.ssafy.ssapilogue.core.domain.Project;
import com.ssafy.ssapilogue.core.domain.User;
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

    @Override
    public List<FindBugReportDto> findBugReports(Long projectId) {
        List<FindBugReportDto> findBugReportDtos = new ArrayList<>();

        List<BugReport> bugReports = bugReportRepository.findAllByProjectIdOrderById(projectId);
        for (BugReport bugReport : bugReports) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String createAt = bugReport.getCreatedAt().format(formatter);

            findBugReportDtos.add(new FindBugReportDto(bugReport, createAt));
        }

        return findBugReportDtos;
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
}
