package com.ssafy.ssapilogue.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("DeleteSurveyReqDto")
public class DeleteSurveyReqDto {

    @ApiModelProperty(value = "삭제할 설문조사 id 리스트", required = true)
    private List<String> surveyIds;
}
