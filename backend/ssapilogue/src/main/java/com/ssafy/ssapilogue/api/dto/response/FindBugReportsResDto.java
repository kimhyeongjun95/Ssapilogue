package com.ssafy.ssapilogue.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("FindBugReportDto")
public class FindBugReportsResDto {

    @ApiModelProperty(value = "전체 버그 리포트 개수", example = "26")
    private Integer totalCount;

    @ApiModelProperty(value = "해결 버그 리포트 개수", example = "18")
    private Integer solvedCount;

    @ApiModelProperty(value = "미해결 버그 리포트 개수", example = "8")
    private Integer unsolvedCount;

    @ApiModelProperty(value = "전체 버그 리포트")
    private List<FindBugReportResDto> bugReports;

}
