package com.ssafy.ssapilogue.api.dto.response;

import com.ssafy.ssapilogue.core.domain.BugReportComment;
import com.ssafy.ssapilogue.core.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("FindBugReportCommentResDto")
public class FindBugReportCommentResDto {

    @ApiModelProperty(value = "버그 리포트 댓글 아이디", example = "1")
    private Long bugCoId;

    @ApiModelProperty(value = "버그 리포트 댓글 작성자 닉네임", example = "동균")
    private String nickname;

    @ApiModelProperty(value = "유저 사진 경로", example = "ftpServerUrl/pic.jpg")
    private String profileImage;

    @ApiModelProperty(value = "버그 리포트 댓글 내용", example = "잘 보고 갑니다~")
    private String content;

    @ApiModelProperty(value = "버그 리포트 댓글 작성일", example = "2022-02-01 23:59")
    private String createAt;

    public FindBugReportCommentResDto(BugReportComment bugReportComment, User user, String createAt) {
        bugCoId = bugReportComment.getId();
        nickname = user.getNickname();
        profileImage = user.getImage();
        content = bugReportComment.getContent();
        this.createAt = createAt;
    }
}
