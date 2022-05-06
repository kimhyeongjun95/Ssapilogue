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
@ApiModel("CreateBugReportReqDto")
public class CreateBugReportReqDto {

    @NotNull
    @ApiModelProperty(value = "버그 리포트 제목", example = "여기 클릭이 안돼요!!")
    private String title;

    @NotNull
    @ApiModelProperty(value = "버그 리포트 내용", example = "형준이가 클릭이 안돼요!!")
    private String content;
}
