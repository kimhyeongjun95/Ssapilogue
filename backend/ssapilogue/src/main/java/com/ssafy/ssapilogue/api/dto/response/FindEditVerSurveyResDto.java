package com.ssafy.ssapilogue.api.dto.response;

import com.ssafy.ssapilogue.core.domain.Survey;
import com.ssafy.ssapilogue.core.domain.SurveyType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@ApiModel("FindEditVerSurveyResDto")
public class FindEditVerSurveyResDto {

    @ApiModelProperty(value = "설문조사 id")
    private String surveyId;

    @ApiModelProperty(value = "설문조사 제목", example = "싸필로그가 유용했나요?")
    private String title;

    @ApiModelProperty(value = "설문조사 타입", example = "객관식")
    private SurveyType surveyType;

    @ApiModelProperty(value = "객관식 질문 옵션")
    private List<String> surveyOptions;

    @ApiModelProperty(value = "Front 사용 Column")
    private String answer;

    public FindEditVerSurveyResDto(Survey survey, List<String> surveyOptions, String answer) {
        surveyId = survey.getId();
        title = survey.getTitle();
        surveyType = survey.getSurveyType();
        this.surveyOptions = surveyOptions;
        this.answer = answer;
    }
}
