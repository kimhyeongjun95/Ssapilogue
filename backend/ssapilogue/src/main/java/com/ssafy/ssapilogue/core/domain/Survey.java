package com.ssafy.ssapilogue.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Document(collection = "survey")
@Getter
@NoArgsConstructor
public class Survey {

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
}
