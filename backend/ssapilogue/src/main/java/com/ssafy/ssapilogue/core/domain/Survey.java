package com.ssafy.ssapilogue.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@Document(collection = "survey")
@Getter
@NoArgsConstructor
public class Survey {

    @Transient // 영속성 필드에서 제외
    public static final String SEQUENCE_NAME = "survey_sequence";

    @Id
    private Long id;

    private Project project;

    private String title;

    private SurveyType surveyType;

    List<SurveyOption> surveyOptions;

    @Builder
    public Survey(Project project, String title, SurveyType surveyType, List<SurveyOption> surveyOptions) {
        this.project = project;
        this.title = title;
        this.surveyType = surveyType;
        this.surveyOptions = surveyOptions;
    }

    public void addSurveyOptions(List surveyOptions) {
        this.surveyOptions = surveyOptions;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
