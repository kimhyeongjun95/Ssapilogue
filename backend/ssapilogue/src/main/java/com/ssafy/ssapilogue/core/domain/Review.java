package com.ssafy.ssapilogue.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Document(collection = "review")
@Getter
@NoArgsConstructor
public class Review {

    @Id
    private String id;

    private String userEmail;

    @DBRef
    private Survey survey;

    @DBRef
    private SurveyOption surveyOption;

    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

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
