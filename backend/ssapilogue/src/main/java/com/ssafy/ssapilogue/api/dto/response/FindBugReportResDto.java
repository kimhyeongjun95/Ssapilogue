package com.ssafy.ssapilogue.api.dto.response;

import com.ssafy.ssapilogue.core.domain.BugReport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("FindBugReportDto")
public class FindBugReportResDto {

    @ApiModelProperty(value = "버그 리포트 아이디", example = "1")
    private Long bugId;

    @ApiModelProperty(value = "버그 리포트 작성자 닉네임", example = "동균")
    private String nickname;

    @ApiModelProperty(value = "유저 사진 경로", example = "ftpServerUrl/pic.jpg")
    private String profileImage;

    @ApiModelProperty(value = "버그 리포트 제목", example = "여기 클릭이 안돼요!!")
    private String title;

    @ApiModelProperty(value = "버그 리포트 내용", example = "형준이가 클릭이 안돼요!!")
    private String content;

    @ApiModelProperty(value = "버그 리포트 해결 여부", example = "false")
    private Boolean isSolved;

    @ApiModelProperty(value = "버그 리포트 작성일", example = "2022-02-01 23:59")
    private String createAt;

    public FindBugReportResDto(BugReport bugReport, String createAt) {
        bugId = bugReport.getId();
        nickname = bugReport.getUser().getNickname();
        profileImage = bugReport.getUser().getImage();
        title = bugReport.getTitle();
        content = bugReport.getContent();
        isSolved = bugReport.getIsSolved();
        this.createAt = createAt;
    }
}
