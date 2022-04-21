package com.ssafy.ssapilogue;

import com.ssafy.ssapilogue.api.dto.request.CreateSurveyOptionReqDto;
import com.ssafy.ssapilogue.api.dto.request.CreateSurveyReqDto;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = SsapilogueApplication.class)
@DataMongoTest
//@TestPropertySource(properties = "spring.mongodb.embedded.version=3.4.5")
//@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DirtiesContext
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
        Long projectId = savedProject.getId();

        CreateSurveyReqDto surveyReqDto1 = new CreateSurveyReqDto(projectId, "싸필로그에 대해 한줄평을 남겨주세요.", "주관식", null);
        surveyService.createSurvey(surveyReqDto1);

        CreateSurveyReqDto surveyReqDto2 = new CreateSurveyReqDto(projectId, "싸필로그의 장점은 무엇인가요?", "주관식", null);
        surveyService.createSurvey(surveyReqDto2);

        List<FindSurveyResDto> resDtoList = surveyService.findSurveys(projectId);

        assertThat(resDtoList.size()).isEqualTo(2);
    }

    @Test
    public void createSurveyTest() {
        CreateSurveyOptionReqDto surveyOptionReqDto1 = new CreateSurveyOptionReqDto(1, "만족");
        CreateSurveyOptionReqDto surveyOptionReqDto2 = new CreateSurveyOptionReqDto(2, "보통");
        CreateSurveyOptionReqDto surveyOptionReqDto3 = new CreateSurveyOptionReqDto(3, "불만족");

        List<CreateSurveyOptionReqDto> surveyOptions = new ArrayList<>();
        surveyOptions.add(surveyOptionReqDto1);
        surveyOptions.add(surveyOptionReqDto2);
        surveyOptions.add(surveyOptionReqDto3);

        // 객관식
        CreateSurveyReqDto surveyReqDto1 = new CreateSurveyReqDto(savedProject.getId(), "싸필로그가 만족스럽나요?", "객관식", surveyOptions);
        Long surveyId1 = surveyService.createSurvey(surveyReqDto1);
        Optional<Survey> survey1 = surveyRepository.findById(surveyId1);

        // 주관식
        CreateSurveyReqDto surveyReqDto2 = new CreateSurveyReqDto(savedProject.getId(), "싸필로그에 대해 한줄평을 남겨주세요.", "주관식", null);
        Long surveyId2 = surveyService.createSurvey(surveyReqDto2);
        Optional<Survey> survey2 = surveyRepository.findById(surveyId2);

        assertThat(survey1.get().getTitle()).isEqualTo("싸필로그가 만족스럽나요?");
        assertThat(survey1.get().getSurveyType()).isEqualTo(SurveyType.객관식);
        assertThat(survey1.get().getSurveyOptions().size()).isEqualTo(3);

        assertThat(survey2.get().getTitle()).isEqualTo("싸필로그에 대해 한줄평을 남겨주세요.");
        assertThat(survey2.get().getSurveyType()).isEqualTo(SurveyType.주관식);
        assertThat(survey2.get().getSurveyOptions().size()).isEqualTo(0);
    }

    @Test
    public void deleteSurveyTest() {
        CreateSurveyReqDto surveyReqDto = new CreateSurveyReqDto(savedProject.getId(), "싸필로그에 대해 한줄평을 남겨주세요.", "주관식", null);
        Long surveyId = surveyService.createSurvey(surveyReqDto);

        surveyService.deleteSurvey(surveyId);

        Optional<Survey> findSurvey = surveyRepository.findById(surveyId);
        assertThat(findSurvey.isPresent()).isEqualTo(false);
    }

    private Project createProject() {
        Project project = Project.builder()
                .title("싸필로그")
                .introduce("당신의 프로젝트를 홍보해드립니다.")
                .category(Category.자율)
                .gitAddress("https://github.com/hyunse0")
                .build();

        savedProject = projectRepository.save(project);
        return savedProject;
    }
}
