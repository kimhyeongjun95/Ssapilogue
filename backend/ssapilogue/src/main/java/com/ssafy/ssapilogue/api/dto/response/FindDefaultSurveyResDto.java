package com.ssafy.ssapilogue.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@ApiModel("FindDefaultSurveyResDto")
public class FindDefaultSurveyResDto {

    @ApiModelProperty(value = "설문조사 제목", example = "싸필로그가 유용했나요?")
    private String title;

    @ApiModelProperty(value = "설문조사 타입", example = "객관식")
    private String surveyType;

    @ApiModelProperty(value = "객관식 질문 옵션")
    private List<String> surveyOptions;

    public FindDefaultSurveyResDto(String projectTitle, String title, String surveyType, List<String> surveyOptions) {
        this.title = projectTitle + title;
        this.surveyType = surveyType;
        this.surveyOptions = surveyOptions;
    }
}
