package com.ssafy.ssapilogue.api.config;

import com.ssafy.ssapilogue.api.service.SequenceGeneratorService;
import com.ssafy.ssapilogue.core.domain.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SurveyListener extends AbstractMongoEventListener<Survey> {

    private final SequenceGeneratorService sequenceGeneratorService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Survey> event) {
        event.getSource().setId(sequenceGeneratorService.generateSequence(Survey.SEQUENCE_NAME));
    }
}
