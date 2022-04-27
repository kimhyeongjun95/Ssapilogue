package com.ssafy.ssapilogue.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "survey_option")
@Getter
@NoArgsConstructor
public class SurveyOption {

    @Id
    private String id;

    private String surveyId;

    private String content;

    @Builder
    public SurveyOption(String surveyId, String content) {
        this.surveyId = surveyId;
        this.content = content;
    }
}
