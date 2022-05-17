package com.ssafy.ssapilogue.api.dto.response;

import com.ssafy.ssapilogue.core.domain.BugReport;
import com.ssafy.ssapilogue.core.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@ApiModel("FindBugReportDetailResDto")
public class FindBugReportDetailResDto {

    @ApiModelProperty(value = "버그 리포트 아이디", example = "1")
    private Long bugId;

    @ApiModelProperty(value = "버그 리포트 작성자 닉네임", example = "동균")
    private String nickname;

    @ApiModelProperty(value = "이메일", example = "test1234@naver.com")
    private String email;

    @ApiModelProperty(value = "유저 사진 경로", example = "ftpServerUrl/pic.jpg")
    private String profileImage;

    @ApiModelProperty(value = "버그 리포트 제목", example = "여기 클릭이 안돼요!!")
    private String title;

    @ApiModelProperty(value = "버그 리포트 내용", example = "형준이가 클릭이 안돼요!!")
    private String content;

    @ApiModelProperty(value = "버그 리포트 작성일", example = "2022-02-01 23:59")
    private String createAt;

    @ApiModelProperty(value = "댓글 수", example = "20")
    private int commentCnt;

    @ApiModelProperty(value = "댓글 리스트")
    private List<FindBugReportCommentResDto> comments;

    public FindBugReportDetailResDto(BugReport bugReport, User user, String createAt, List<FindBugReportCommentResDto> comments) {
        bugId = bugReport.getId();
        nickname = user.getNickname();
        email = bugReport.getUser().getEmail();
        profileImage = user.getImage();
        title = bugReport.getTitle();
        content = bugReport.getContent();
        this.createAt = createAt;
        commentCnt = comments.size();
        this.comments = comments;
    }
}
