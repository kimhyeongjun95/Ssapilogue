package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.CreateSurveyReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindSurveyResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class SurveyServiceImpl implements SurveyService {
    @Override
    public List<FindSurveyResDto> findSurvey(Long surveyId) {
        return null;
    }

    @Override
    public Long createSurvey(CreateSurveyReqDto createSurveyReqDto) {
        return null;
    }
}
