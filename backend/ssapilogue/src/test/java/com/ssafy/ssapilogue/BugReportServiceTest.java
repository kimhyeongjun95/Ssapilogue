package com.ssafy.ssapilogue;

import com.ssafy.ssapilogue.api.dto.request.CreateBugReportReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindBugReportDetailResDto;
import com.ssafy.ssapilogue.api.dto.response.FindBugReportsResDto;
import com.ssafy.ssapilogue.api.service.BugReportService;
import com.ssafy.ssapilogue.core.domain.*;
import com.ssafy.ssapilogue.core.repository.BugReportCommentRepository;
import com.ssafy.ssapilogue.core.repository.BugReportRepository;
import com.ssafy.ssapilogue.core.repository.ProjectRepository;
import com.ssafy.ssapilogue.core.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BugReportServiceTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BugReportRepository bugReportRepository;

    @Autowired
    private BugReportCommentRepository bugReportCommentRepository;

    @Autowired
    private BugReportService bugReportService;

    @Test
    public void findBugReportsTest() throws Exception {
        BugReport bugReport1 = BugReport.builder()
                .project(savedProject)
                .user(savedUser)
                .title("여기 클릭이 안돼요!!")
                .content("형준이가 클릭이 안돼요!!")
                .isSolved(false)
                .build();
        bugReportRepository.save(bugReport1);

        BugReport bugReport2 = BugReport.builder()
                .project(savedProject)
                .user(savedUser)
                .title("페이지가 안넘어가네요.")
                .content("상세페이지로 안넘어가요!")
                .isSolved(true)
                .build();
        bugReportRepository.save(bugReport2);

        FindBugReportsResDto result = bugReportService.findBugReports(savedProject.getId());

        assertThat(result.getTotalCount()).isEqualTo(2);
        assertThat(result.getSolvedCount()).isEqualTo(1);
        assertThat(result.getUnsolvedCount()).isEqualTo(1);

        assertThat(result.getBugReports().get(0).getNickname()).isEqualTo(savedUser.getNickname());
        assertThat(result.getBugReports().get(0).getTitle()).isEqualTo("여기 클릭이 안돼요!!");
        assertThat(result.getBugReports().get(0).getContent()).isEqualTo("형준이가 클릭이 안돼요!!");
        assertThat(result.getBugReports().get(0).getIsSolved()).isEqualTo(false);
    }

    @Test
    public void createBugReportTest() throws Exception {
        CreateBugReportReqDto createBugReportReqDto = new CreateBugReportReqDto("여기 클릭이 안돼요!!", "형준이가 클릭이 안돼요!!");
        Long bugId = bugReportService.createBugReport(savedProject.getId(), savedUser.getEmail(), createBugReportReqDto);

        Optional<BugReport> bugReport = bugReportRepository.findById(bugId);
        assertThat(bugReport.get().getTitle()).isEqualTo("여기 클릭이 안돼요!!");
        assertThat(bugReport.get().getContent()).isEqualTo("형준이가 클릭이 안돼요!!");
    }

    @Test
    public void findBugReportDetailTest() throws Exception {
        BugReport bugReport = BugReport.builder()
                .project(savedProject)
                .user(savedUser)
                .title("여기 클릭이 안돼요!!")
                .content("형준이가 클릭이 안돼요!!")
                .isSolved(false)
                .build();
        BugReport savedBugReport = bugReportRepository.save(bugReport);

        BugReportComment comment = BugReportComment.builder()
                .bugReport(bugReport)
                .user(savedUser)
                .content("멋있어요!")
                .build();
        bugReportCommentRepository.save(comment);

        FindBugReportDetailResDto result = bugReportService.findBugReportDetail(savedBugReport.getId());

        assertThat(result.getNickname()).isEqualTo(savedUser.getNickname());
        assertThat(result.getTitle()).isEqualTo("여기 클릭이 안돼요!!");
        assertThat(result.getContent()).isEqualTo("형준이가 클릭이 안돼요!!");
        assertThat(result.getCommentCnt()).isEqualTo(1);
    }

    @Test
    public void updateBugReportTest() throws Exception {
        BugReport bugReport = BugReport.builder()
                .project(savedProject)
                .user(savedUser)
                .title("여기 클릭이 안돼요!!")
                .content("형준이가 클릭이 안돼요!!")
                .isSolved(false)
                .build();
        BugReport savedBugReport = bugReportRepository.save(bugReport);

        CreateBugReportReqDto createBugReportReqDto = new CreateBugReportReqDto("페이지가 안넘어가네요.", "상세페이지로 안넘어가요!");

        bugReportService.updateBugReport(savedBugReport.getId(), createBugReportReqDto);

        assertThat(savedBugReport.getTitle()).isEqualTo("페이지가 안넘어가네요.");
        assertThat(savedBugReport.getContent()).isEqualTo("상세페이지로 안넘어가요!");
    }

    @Test
    public void deleteBugReportTest() throws Exception {
        BugReport bugReport = BugReport.builder()
                .project(savedProject)
                .user(savedUser)
                .title("여기 클릭이 안돼요!!")
                .content("형준이가 클릭이 안돼요!!")
                .isSolved(false)
                .build();
        BugReport savedBugReport = bugReportRepository.save(bugReport);

        bugReportService.deleteBugReport(savedBugReport.getId());

        Optional<BugReport> findBugReport = bugReportRepository.findById(savedBugReport.getId());
        assertThat(findBugReport.isPresent()).isEqualTo(false);
    }

    @Test
    public void solvedBugReportTest() throws Exception {
        BugReport bugReport = BugReport.builder()
                .project(savedProject)
                .user(savedUser)
                .title("여기 클릭이 안돼요!!")
                .content("형준이가 클릭이 안돼요!!")
                .isSolved(false)
                .build();
        BugReport savedBugReport = bugReportRepository.save(bugReport);

        bugReportService.solvedBugReport(savedBugReport.getId());

        assertThat(savedBugReport.getIsSolved()).isEqualTo(true);
    }

    @BeforeEach
    void before() {
        createProject();
        createUser();
    }

    private Project savedProject;

    private User savedUser;

    private void createProject() {
        Project project = Project.builder()
                .title("싸필로그")
                .introduce("당신의 프로젝트를 홍보해드립니다.")
                .category(Category.자율)
                .gitAddress("https://github.com/hyunse0")
                .build();

        savedProject = projectRepository.save(project);
    }

    private void createUser() {
        User user = User.builder()
                .email("gkgustj@naver.com")
                .github("github")
                .greeting("greeting")
                .image("image")
                .password("Sgkgustj@naver.com6")
                .userId("ep47t86u6ig1tgwfurkxzsx9pe")
                .nickname("hs")
                .username("hs")
                .build();

        savedUser = userRepository.save(user);
    }
}
