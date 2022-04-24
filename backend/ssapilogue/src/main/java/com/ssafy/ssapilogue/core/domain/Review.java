package com.ssafy.ssapilogue.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.Transient;

@Document(collection = "review")
@Getter
@NoArgsConstructor
public class Review {

    @Transient
    public static final String SEQUENCE_NAME = "review_sequence";

    @Id
    private Long id;

//    private Survey survey;

//    private User user;

    private Integer index;

    private String content;

    @Builder
    public Review(Long id, Integer index, String content) {
        this.id = id;
        this.index = index;
        this.content = content;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
