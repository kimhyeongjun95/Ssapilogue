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

    @ApiModelProperty(value = "옵션 번호", example = "1")
    private Integer index;

    @ApiModelProperty(value = "옵션 내용", example = "매우 만족")
    private String content;

    public FindSurveyOptionResDto(SurveyOption surveyOption) {
        index = surveyOption.getIndex();
        content = surveyOption.getContent();
    }
}
