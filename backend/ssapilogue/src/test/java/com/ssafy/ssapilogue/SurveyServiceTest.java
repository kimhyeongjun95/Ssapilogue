package com.ssafy.ssapilogue;

import com.ssafy.ssapilogue.api.dto.request.CreateSurveyReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindDefaultSurveyResDto;
import com.ssafy.ssapilogue.api.dto.response.FindSurveyResDto;
import com.ssafy.ssapilogue.api.service.SurveyService;
import com.ssafy.ssapilogue.core.domain.Category;
import com.ssafy.ssapilogue.core.domain.Project;
import com.ssafy.ssapilogue.core.domain.Survey;
import com.ssafy.ssapilogue.core.domain.SurveyType;
import com.ssafy.ssapilogue.core.repository.ProjectRepository;
import com.ssafy.ssapilogue.core.repository.SurveyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class SurveyServiceTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyService surveyService;

    @BeforeEach
    void before() {
        createProject();
    }

    private Project savedProject;

    @Test
    public void findSurveysTest() {
        Survey survey1 = Survey.builder()
                .projectId(savedProject.getId())
                .title("싸필로그에 대해 한줄평을 남겨주세요.")
                .surveyType(SurveyType.주관식)
                .build();
        surveyRepository.save(survey1);

        Survey survey2 = Survey.builder()
                .projectId(savedProject.getId())
                .title("싸필로그의 장점은 무엇인가요?")
                .surveyType(SurveyType.주관식)
                .build();
        surveyRepository.save(survey2);

        List<FindSurveyResDto> result = surveyService.findSurveys(savedProject.getId());
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void createSurveyTest() {
        List<String> surveyOptions = new ArrayList<>();
        surveyOptions.add("만족");
        surveyOptions.add("보통");
        surveyOptions.add("불만족");

        List<CreateSurveyReqDto> createSurveyReqDtos = new ArrayList<>();
        // 객관식
        CreateSurveyReqDto surveyReqDto1 = new CreateSurveyReqDto("싸필로그가 만족스럽나요?", "객관식", surveyOptions);
        createSurveyReqDtos.add(surveyReqDto1);
        // 주관식
        CreateSurveyReqDto surveyReqDto2 = new CreateSurveyReqDto("싸필로그에 대해 한줄평을 남겨주세요.", "주관식", null);
        createSurveyReqDtos.add(surveyReqDto2);

        List<String> surveyIds = surveyService.createSurvey(savedProject.getId(), createSurveyReqDtos);

        Optional<Survey> survey1 = surveyRepository.findById(surveyIds.get(0));
        assertThat(survey1.get().getTitle()).isEqualTo("싸필로그가 만족스럽나요?");
        assertThat(survey1.get().getSurveyType()).isEqualTo(SurveyType.객관식);
        assertThat(survey1.get().getSurveyOptions().size()).isEqualTo(3);

        Optional<Survey> survey2 = surveyRepository.findById(surveyIds.get(1));
        assertThat(survey2.get().getTitle()).isEqualTo("싸필로그에 대해 한줄평을 남겨주세요.");
        assertThat(survey2.get().getSurveyType()).isEqualTo(SurveyType.주관식);
    }

    @Test
    public void deleteSurveyTest() {
        Survey survey = Survey.builder()
                .projectId(savedProject.getId())
                .title("싸필로그에 대해 한줄평을 남겨주세요.")
                .surveyType(SurveyType.주관식)
                .build();
        Survey savedSurvey = surveyRepository.save(survey);

        surveyService.deleteSurvey(savedSurvey.getId());

        Optional<Survey> findSurvey = surveyRepository.findById(savedSurvey.getId());
        assertThat(findSurvey.isPresent()).isEqualTo(false);
    }

    @Test
    public void defaultSurveyTest() {
        List<FindDefaultSurveyResDto> result = surveyService.defaultSurvey(savedProject.getTitle());
        assertThat(result.size()).isEqualTo(5);
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
}
