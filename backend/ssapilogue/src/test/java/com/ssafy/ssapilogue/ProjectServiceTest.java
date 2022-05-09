package com.ssafy.ssapilogue;

import com.ssafy.ssapilogue.api.dto.request.CreateProjectReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectDetailResDto;
import com.ssafy.ssapilogue.api.dto.response.FindProjectResDto;
import com.ssafy.ssapilogue.api.service.ProjectService;
import com.ssafy.ssapilogue.core.domain.Category;
import com.ssafy.ssapilogue.core.domain.Project;
import com.ssafy.ssapilogue.core.domain.User;
import com.ssafy.ssapilogue.core.repository.ProjectRepository;
import com.ssafy.ssapilogue.core.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional(propagation = Propagation.SUPPORTS)
public class ProjectServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectRepository projectRepository;

    @BeforeEach
    void beforeEach() {
        createUser();
        createProjectReqDto();
    }

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
        projectRepository.deleteAll();
    }

    private User saveUser;
    private CreateProjectReqDto createProjectReqDto;
    private CreateProjectReqDto updateProjectReqDto;

    @Test
    public void createProjectTest() {
        // given
        Long projectId = projectService.createProject(createProjectReqDto, saveUser.getEmail());

        // when
        Project project = projectRepository.getById(projectId);

        // then
        assertThat(project).isNotNull();
    }

    @Test
    public void findProjectTest() {
        // given
        Long projectId = projectService.createProject(createProjectReqDto, saveUser.getEmail());

        // when
        Project project = projectRepository.getById(projectId);

        // then
        assertThat(projectId).isEqualTo(project.getId());
    }

    @Test
    public void findProjectDetailResDtoTest() {
        // given
        Long projectId = projectService.createProject(createProjectReqDto, saveUser.getEmail());

        // when
        FindProjectDetailResDto findProjectDetailResDto = projectService.findProjectDetail(projectId, saveUser.getEmail());

        // then
        assertThat(findProjectDetailResDto.getProjectId()).isEqualTo(projectId);
        assertThat(findProjectDetailResDto.getMember().size()).isEqualTo(1);
        assertThat(findProjectDetailResDto.getAnonymousMember().size()).isEqualTo(2);
        assertThat(findProjectDetailResDto.getTechStack().size()).isEqualTo(2);
        assertThat(findProjectDetailResDto.getIsLiked()).isEqualTo(false);
    }

    @Test
    public void updateProjectTest() {
        // given
        Long projectId = projectService.createProject(createProjectReqDto, saveUser.getEmail());

        // when
        updateProjectReqDto();
        projectService.updateProject(projectId, updateProjectReqDto);
        FindProjectDetailResDto findProjectDetailResDto = projectService.findProjectDetail(projectId, saveUser.getEmail());

        // then
        assertThat(findProjectDetailResDto.getTitle()).isEqualTo("라이키수정");
        assertThat(findProjectDetailResDto.getCategory()).isEqualTo(Category.valueOf("자율"));
        assertThat(findProjectDetailResDto.getTechStack().size()).isEqualTo(3);
    }

    @Test
    public void deleteProjectTest() {
        // given
        Long projectId = projectService.createProject(createProjectReqDto, saveUser.getEmail());

        // when
        projectService.deleteProject(projectId, saveUser.getEmail());

        // then
        assertThat(projectRepository.findById(projectId).isPresent()).isEqualTo(false);
    }

    @Test
    public void searchProjectsByTitleTest() {
        // given
        Long projectId = projectService.createProject(createProjectReqDto, saveUser.getEmail());

        // when
        List<FindProjectResDto> findProjectResDtos = projectService.searchProjectsByTitle("랑", saveUser.getEmail());
        System.out.println(findProjectResDtos);

        // then
        assertThat(findProjectResDtos).size().isEqualTo(1);
        assertThat(findProjectResDtos.get(0).getTitle()).isEqualTo("라이키");
    }

    private void createUser() {
        User user = User.builder()
                .email("khyunchoi@naver.com")
                .password(BCrypt.hashpw("Skhyunchoi@naver.com6", BCrypt.gensalt()))
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

    private void updateProjectReqDto() {
        List<String> techStack = new ArrayList<>();
        techStack.add("React");
        techStack.add("Spring");
        techStack.add("Java");
        List<String> member = new ArrayList<>();
        member.add("최강현[광주_1반_C104]팀원");
        member.add("정동균[광주_1반_C104]팀원");
        member.add("서울_6반_나준엽");
        updateProjectReqDto = new CreateProjectReqDto("라이키수정", "라이키 프로젝트", "자율",
                techStack, member, "https://j6ssafy.c104.com/", "https://github.com/khyunchoi/Rikey",
                "http://k6c104.p.ssafy.io/images/projectImg/b0cf5863-8ab5-4c4b-8b93-62bc634abb5b_개발짤.jpg", 1, "readme 어쩌구");
    }
}
