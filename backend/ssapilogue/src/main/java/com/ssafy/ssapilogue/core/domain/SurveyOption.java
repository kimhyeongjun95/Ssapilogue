package com.ssafy.ssapilogue.core.domain;

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

    private Survey survey;

    private Integer index;

    private String content;
}
