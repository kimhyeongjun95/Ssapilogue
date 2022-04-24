package com.ssafy.ssapilogue.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "review")
@Getter
@NoArgsConstructor
public class Review {

    @Id
    private String id;

//    private Survey survey;

//    private User user;

    private Integer index;

    private String content;
}
