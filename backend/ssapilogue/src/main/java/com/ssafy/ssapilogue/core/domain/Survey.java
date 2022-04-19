package com.ssafy.ssapilogue.core.domain;

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
    private String id;

//    private Project project;

    private String title;

    private SurveyType surveyType;

    private Boolean is_delete;

    List<SurveyOption> surveyOptions;
}
