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
@ApiModel("CreateBugReportCommentReqDto")
public class CreateBugReportCommentReqDto {

    @NotNull
    @ApiModelProperty(value = "버그 리포트 댓글 내용", example = "요렇게 조렇게 해보세용~")
    private String content;
}
