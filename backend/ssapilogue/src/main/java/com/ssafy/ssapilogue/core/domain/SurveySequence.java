package com.ssafy.ssapilogue.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "survey_sequence")
@Getter
@NoArgsConstructor
public class SurveySequence {

    @Id
    private String id;

    private Long seq;
}
