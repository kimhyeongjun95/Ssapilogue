package com.ssafy.ssapilogue.core.repository;

import com.ssafy.ssapilogue.core.domain.Review;
import com.ssafy.ssapilogue.core.domain.Survey;
import com.ssafy.ssapilogue.core.domain.SurveyOption;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findAllBySurveyOrOrderByCreatedAtDesc(Survey survey);
    Integer countAllBySurveyOption(SurveyOption surveyOption);
}
