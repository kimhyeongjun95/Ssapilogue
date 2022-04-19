package com.ssafy.ssapilogue.core.repository;

import com.ssafy.ssapilogue.core.domain.SurveyOption;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SurveyOptionRepository extends MongoRepository<SurveyOption, Long> {
}
