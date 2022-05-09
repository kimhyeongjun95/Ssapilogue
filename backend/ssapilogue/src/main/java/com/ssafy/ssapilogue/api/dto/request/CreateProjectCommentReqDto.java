package com.ssafy.ssapilogue.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@ApiModel("CreateProjectCommentReqDto")
public class CreateProjectCommentReqDto {

    @NotNull
    @ApiModelProperty(value = "댓글 내용", example = "프로젝트 잘 봤습니다!", required = true)
    private String content;
}
