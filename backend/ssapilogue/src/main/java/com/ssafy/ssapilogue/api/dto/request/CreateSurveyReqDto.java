package com.ssafy.ssapilogue.api.dto.request;

import com.ssafy.ssapilogue.core.domain.SurveyType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@ApiModel("CreateSurveyReqDto")
public class CreateSurveyReqDto {
    
    @NotNull
    @ApiModelProperty(value = "프로젝트 id", required = true, example = "1")
    private Long projectId;

    @NotNull
    @ApiModelProperty(value = "설문조사 제목", required = true, example = "싸필로그가 유용했나요?")
    private String title;

    @NotNull
    @ApiModelProperty(value = "설문조사 타입", required = true, example = "객관식")
    private SurveyType surveyType;
}
