package com.ssafy.ssapilogue.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("FindObjectiveReviewResDto")
public class FindObjectiveReviewResDto {

    @ApiModelProperty(value = "설문조사 옵션 이름", example = "매우 만족")
    private String optionContent;

    @ApiModelProperty(value = "설문조사 옵션 개수", example = "15")
    private Integer count;
}
