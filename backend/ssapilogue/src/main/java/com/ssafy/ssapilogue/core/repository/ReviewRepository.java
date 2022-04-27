package com.ssafy.ssapilogue.core.repository;

import com.ssafy.ssapilogue.core.domain.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review, String> {
}
