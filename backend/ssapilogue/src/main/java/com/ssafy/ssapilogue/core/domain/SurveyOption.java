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
    private Long id;

    private Long surveyId;

    private Integer index;

    private String content;

    @Builder
    public SurveyOption(Long surveyId, Integer index, String content) {
        this.surveyId = surveyId;
        this.index = index;
        this.content = content;
    }
}
