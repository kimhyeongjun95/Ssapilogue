package com.ssafy.ssapilogue;

import com.ssafy.ssapilogue.api.dto.request.CreateBugReportCommentReqDto;
import com.ssafy.ssapilogue.api.service.BugReportCommentService;
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
public class BugReportCommentServiceTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BugReportRepository bugReportRepository;

    @Autowired
    private BugReportCommentRepository bugReportCommentRepository;

    @Autowired
    private BugReportCommentService bugReportCommentService;

    @BeforeEach
    void before() {
        createProject();
        createUser();
        createBugReport();
    }

    private Project savedProject;

    private User savedUser;

    private BugReport savedBugReport;

    @Test
    public void createBugReportCommentTest() throws Exception {
        CreateBugReportCommentReqDto createBugReportCommentReqDto = new CreateBugReportCommentReqDto("요렇게 조렇게 해보세용~");
        Long bugCoId = bugReportCommentService.createBugReportComment(savedBugReport.getId(), savedUser.getEmail(), createBugReportCommentReqDto);

        Optional<BugReportComment> comment = bugReportCommentRepository.findById(bugCoId);
        assertThat(comment.get().getUser().getEmail()).isEqualTo(savedUser.getEmail());
        assertThat(comment.get().getContent()).isEqualTo("요렇게 조렇게 해보세용~");
    }

    @Test
    public void deleteBugReportCommentTest() throws Exception {
        BugReportComment comment = BugReportComment.builder()
                .bugReport(savedBugReport)
                .user(savedUser)
                .content("멋있어요!")
                .build();
        BugReportComment savedComment = bugReportCommentRepository.save(comment);

        bugReportCommentService.deleteBugReportComment(savedComment.getId());

        Optional<BugReportComment> findComment = bugReportCommentRepository.findById(savedComment.getId());
        assertThat(findComment.isPresent()).isEqualTo(false);
    }

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

    private void createBugReport() {
        BugReport bugReport = BugReport.builder()
                .project(savedProject)
                .user(savedUser)
                .title("여기 클릭이 안돼요!!")
                .content("형준이가 클릭이 안돼요!!")
                .isSolved(false)
                .build();

        savedBugReport = bugReportRepository.save(bugReport);
    }
}
