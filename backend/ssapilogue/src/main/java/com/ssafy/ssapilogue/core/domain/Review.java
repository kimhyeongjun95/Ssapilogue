package com.ssafy.ssapilogue.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EntityListeners;
import javax.persistence.Id;

@Document(collection = "review")
@Getter
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class})
public class Review {

    @Id
    private String id;

    private String userEmail;

    @DBRef
    private Survey survey;

    @DBRef
    private SurveyOption surveyOption;

    private String content;

    @Builder
    public Review(String userEmail, Survey survey, SurveyOption surveyOption, String content) {
        this.userEmail = userEmail;
        this.survey = survey;
        this.surveyOption = surveyOption;
        this.content = content;
    }

    public void saveSurveyOption(SurveyOption surveyOption) {
        this.surveyOption = surveyOption;
    }

    public void saveContent(String content) {
        this.content = content;
    }
}
