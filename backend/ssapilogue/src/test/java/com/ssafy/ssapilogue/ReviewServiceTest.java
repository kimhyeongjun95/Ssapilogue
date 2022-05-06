package com.ssafy.ssapilogue;

import com.ssafy.ssapilogue.api.dto.request.CreateReviewReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindReviewResDto;
import com.ssafy.ssapilogue.api.dto.response.FindSurveyOptionResDto;
import com.ssafy.ssapilogue.api.dto.response.FindSurveyResDto;
import com.ssafy.ssapilogue.api.service.ReviewService;
import com.ssafy.ssapilogue.core.domain.*;
import com.ssafy.ssapilogue.core.repository.*;
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
public class ReviewServiceTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyOptionRepository surveyOptionRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewService reviewService;

    @BeforeEach
    void before() {
        createProject();
        createUser();
        createSurvey();
    }

    private Project savedProject;

    private User savedUser;

    private Survey objectiveSurvey;

    private SurveyOption pickedOption;

    private Survey subjectiveSurvey;

    @Test
    public void findReviewsTest() throws Exception {
        // 객관식
        Review review1 = Review.builder()
                .userEmail(savedUser.getEmail())
                .survey(objectiveSurvey)
                .surveyOption(pickedOption)
                .build();
        reviewRepository.save(review1);

        // 주관식
        Review review2 = Review.builder()
                .userEmail(savedUser.getEmail())
                .survey(subjectiveSurvey)
                .content("매우 유용했습니다.")
                .build();
        reviewRepository.save(review2);

        List<FindReviewResDto> result = reviewService.findReviews(savedProject.getId());

        for (FindReviewResDto findReviewResDto : result) {
            System.out.println(findReviewResDto);
        }

        assertThat(result.get(0).getTotalCount()).isEqualTo(1);
        assertThat(result.get(1).getTotalCount()).isEqualTo(1);
    }

    @Test
    public void createReviewTest() throws Exception {
        List<FindSurveyResDto> findSurveyResDtos = new ArrayList<>();

        // 객관식
        List<FindSurveyOptionResDto> surveyOptions = new ArrayList<>();
        for (SurveyOption surveyOption : surveyOptionRepository.findAllBySurveyId(objectiveSurvey.getId())) {
            surveyOptions.add(new FindSurveyOptionResDto(surveyOption));
        }

        FindSurveyResDto findSurveyResDto1 = new FindSurveyResDto(objectiveSurvey, surveyOptions, pickedOption.getId());
        findSurveyResDtos.add(findSurveyResDto1);

        //주관식
        FindSurveyResDto findSurveyResDto2 = new FindSurveyResDto(subjectiveSurvey, null, "매우 유용했습니다.");
        findSurveyResDtos.add(findSurveyResDto2);

        List<String> reviewIds = reviewService.createReview(savedUser.getEmail(), findSurveyResDtos);

        Optional<Review> review1 = reviewRepository.findById(reviewIds.get(0));
        assertThat(review1.get().getUserEmail()).isEqualTo(savedUser.getEmail());
        assertThat(review1.get().getSurvey().getId()).isEqualTo(objectiveSurvey.getId());
        assertThat(review1.get().getSurveyOption().getId()).isEqualTo(pickedOption.getId());

        Optional<Review> review2 = reviewRepository.findById(reviewIds.get(1));
        assertThat(review2.get().getUserEmail()).isEqualTo(savedUser.getEmail());
        assertThat(review2.get().getSurvey().getId()).isEqualTo(subjectiveSurvey.getId());
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

    private void createSurvey() {
        // 객관식
        Survey survey1 = Survey.builder()
                .projectId(savedProject.getId())
                .title("싸필로그에 얼만큼 만족하나요?")
                .surveyType(SurveyType.객관식)
                .build();

        objectiveSurvey = surveyRepository.save(survey1);

        List<SurveyOption> surveyOptions = new ArrayList<>();

        SurveyOption surveyOption1 = SurveyOption.builder()
                .surveyId(objectiveSurvey.getId())
                .content("만족")
                .build();
        pickedOption = surveyOptionRepository.save(surveyOption1);
        surveyOptions.add(pickedOption);

        SurveyOption surveyOption2 = SurveyOption.builder()
                .surveyId(objectiveSurvey.getId())
                .content("불만족")
                .build();
        surveyOptions.add(surveyOptionRepository.save(surveyOption2));

        objectiveSurvey.addSurveyOptions(surveyOptions);
        surveyRepository.save(objectiveSurvey);

        // 주관식
        Survey survey2 = Survey.builder()
                .projectId(savedProject.getId())
                .title("싸필로그에 한줄평을 남겨주세요.")
                .surveyType(SurveyType.주관식)
                .build();

        subjectiveSurvey = surveyRepository.save(survey2);
    }
}
