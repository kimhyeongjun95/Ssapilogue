package com.ssafy.ssapilogue.core.repository;

import com.ssafy.ssapilogue.core.domain.Survey;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SurveyRepository extends MongoRepository<Survey, String> {
}
