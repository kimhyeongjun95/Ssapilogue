package com.ssafy.ssapilogue.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("CreateCommentReqDto")
public class CreateCommentReqDto {

    @ApiModelProperty(value = "프로젝트 id", example = "1", required = true)
    private Long projectId;

    @ApiModelProperty(value = "댓글 내용", example = "프로젝트 잘 봤습니다!", required = true)
    private String content;
}
