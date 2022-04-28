package com.ssafy.ssapilogue;

import com.ssafy.ssapilogue.api.dto.request.CreateProjectReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectDetailResDto;
import com.ssafy.ssapilogue.api.service.ProjectService;
import com.ssafy.ssapilogue.core.domain.Project;
import com.ssafy.ssapilogue.core.domain.User;
import com.ssafy.ssapilogue.core.repository.ProjectMemberRepository;
import com.ssafy.ssapilogue.core.repository.ProjectRepository;
import com.ssafy.ssapilogue.core.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProjectServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    @BeforeEach
    void init() {
        createUser();
    }

    private User saveUser;
    private CreateProjectReqDto createProjectReqDto;

    @Test
    public void createProjectTest() {
        // given
        createProjectReqDto();

        // when
        Long projectId = projectService.createProject(createProjectReqDto, saveUser.getEmail());
        Project project = projectRepository.getById(projectId);

        // then
        assertThat(project).isNotNull();
    }

    @Test
    public void findProjectTest() {
        // given
        createProjectReqDto();
        Long projectId = projectService.createProject(createProjectReqDto, saveUser.getEmail());

        // when
        Project project = projectRepository.getById(projectId);

        // then
        assertThat(projectId).isEqualTo(project.getId());
    }

    @Test
    public void findProjectDetailResDtoTest() {
        // given
        createProjectReqDto();
        Long projectId = projectService.createProject(createProjectReqDto, saveUser.getEmail());

        // when
        Project project = projectRepository.getById(projectId);
        FindProjectDetailResDto projectDetailResDto = projectService.findProject(projectId, saveUser.getEmail());

        // then
        assertThat(projectDetailResDto.getProjectId()).isEqualTo(projectId);
        assertThat(projectMemberRepository.findByProject(project).size()).isEqualTo(2);
    }

    private void createUser() {
        User user = User.builder()
                .email("khyunchoi@naver.com")
                .password("Skhyunchoi@naver.com6")
                .userId("97uwojff7jri78dimcnb953bna")
                .nickname("최강현[광주_1반_C104]팀원")
                .username("khyunchoi")
                .github("https://github.com/khyunchoi")
                .greeting("안녕하세요")
                .image("")
                .build();

        saveUser = userRepository.save(user);
    }

    private void createProjectReqDto() {
        List<String> techStack = new ArrayList<>();
        techStack.add("ReactNative");
        techStack.add("Spring");
        List<String> member = new ArrayList<>();
        member.add("최강현[광주_1반_C104]팀원");
        member.add("정동균[광주_1반_C104]팀원");
        member.add("서울_6반_나준엽");
        createProjectReqDto = new CreateProjectReqDto("라이키", "라이키 프로젝트", "특화",
                techStack, member, "https://j6ssafy.c104.com/", "https://github.com/khyunchoi/Rikey",
                "http://k6c104.p.ssafy.io/images/projectImg/b0cf5863-8ab5-4c4b-8b93-62bc634abb5b_개발짤.jpg", 1, "readme 어쩌구");
    }
}
