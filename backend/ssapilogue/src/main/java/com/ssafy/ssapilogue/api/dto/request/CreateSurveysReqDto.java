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
@ApiModel("CreateSurveysReqDto")
public class CreateSurveysReqDto {

    @NotNull
    @ApiModelProperty(value = "설문조사 정보", required = true)
    private List<CreateSurveyReqDto> createSurveyReqDtos;
}
