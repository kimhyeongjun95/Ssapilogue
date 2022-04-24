package com.ssafy.ssapilogue.api.mongodbListener;

import com.ssafy.ssapilogue.api.service.SequenceGeneratorService;
import com.ssafy.ssapilogue.core.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReviewListener extends AbstractMongoEventListener<Review> {

    private final SequenceGeneratorService sequenceGeneratorService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Review> event) {
        event.getSource().setId(sequenceGeneratorService.generateSequence(Review.SEQUENCE_NAME));
    }
}
