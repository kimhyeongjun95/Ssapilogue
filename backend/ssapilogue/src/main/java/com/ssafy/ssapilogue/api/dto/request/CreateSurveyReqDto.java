package com.ssafy.ssapilogue.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("CreateSurveyReqDto")
public class CreateSurveyReqDto {

    @ApiModelProperty(value = "설문조사 id", required = true)
    private String surveyId;

    @NotNull
    @ApiModelProperty(value = "설문조사 제목", required = true, example = "싸필로그가 유용했나요?")
    private String title;

    @NotNull
    @ApiModelProperty(value = "설문조사 타입", required = true, example = "객관식")
    private String surveyType;

    @ApiModelProperty(value = "객관식 설문조사 옵션")
    private List<String> surveyOptions;
}
