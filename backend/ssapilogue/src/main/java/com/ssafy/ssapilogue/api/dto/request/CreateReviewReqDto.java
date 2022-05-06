package com.ssafy.ssapilogue.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("CreateReviewReqDto")
public class CreateReviewReqDto {

    @NotNull
    @ApiModelProperty(value = "설문조사 id")
    private String surveyId;

    @ApiModelProperty(value = "객관식 설문조사 옵션 id")
    private String surveyOptionId;

    @ApiModelProperty(value = "주관식 답안", example = "매우 유용했습니다.")
    private String content;
}
