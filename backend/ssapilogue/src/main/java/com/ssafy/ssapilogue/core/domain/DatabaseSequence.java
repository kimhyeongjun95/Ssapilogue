package com.ssafy.ssapilogue.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "database_sequence")
@Getter
@NoArgsConstructor
public class DatabaseSequence {

    @Id
    private String id;

    private Long seq;
}
