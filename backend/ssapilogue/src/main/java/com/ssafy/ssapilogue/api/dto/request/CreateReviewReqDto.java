package com.ssafy.ssapilogue.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("CreateReviewReqDto")
public class CreateReviewReqDto {

    @ApiModelProperty(value = "객관식 선택 문항 번호", example = "1")
    private Integer index;

    @ApiModelProperty(value = "주관식 답안", example = "매우 유용했습니다.")
    private String content;
}
