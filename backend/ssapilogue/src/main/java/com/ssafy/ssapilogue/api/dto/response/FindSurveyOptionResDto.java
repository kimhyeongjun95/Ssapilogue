package com.ssafy.ssapilogue.api.dto.response;

import com.ssafy.ssapilogue.core.domain.SurveyOption;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("FindSurveyOptionResDto")
public class FindSurveyOptionResDto {

    @ApiModelProperty(value = "설문조사 옵션 id")
    private String surveyOptionId;

    @ApiModelProperty(value = "설문조사 옵션 내용")
    private String content;

    public FindSurveyOptionResDto(SurveyOption surveyOption) {
        surveyOptionId = surveyOption.getId();
        content = surveyOption.getContent();
    }
}
