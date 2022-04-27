package com.ssafy.ssapilogue.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.List;

@Document(collection = "survey")
@Getter
@NoArgsConstructor
public class Survey {

    @Id
    private String id;

    private Long projectId;

    private String title;

    private SurveyType surveyType;

    @DBRef
    List<SurveyOption> surveyOptions;

    @Builder
    public Survey(Long projectId, String title, SurveyType surveyType) {
        this.projectId = projectId;
        this.title = title;
        this.surveyType = surveyType;
    }

    public void addSurveyOptions(List<SurveyOption> surveyOptions) {
        this.surveyOptions = surveyOptions;
    }
}
