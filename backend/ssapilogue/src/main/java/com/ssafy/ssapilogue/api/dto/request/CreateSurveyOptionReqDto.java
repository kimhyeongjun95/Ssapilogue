package com.ssafy.ssapilogue.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@ApiModel("CreateSurveyOptionReqDto")
public class CreateSurveyOptionReqDto {

    @NotNull
    @ApiModelProperty(value = "설문조사 id", required = true, example = "1")
    private Long surveyId;

    @NotNull
    @ApiModelProperty(value = "옵션 번호", required = true, example = "1")
    private Integer index;

    @NotNull
    @ApiModelProperty(value = "옵션 내용", required = true, example = "매우 만족")
    private String content;
}
