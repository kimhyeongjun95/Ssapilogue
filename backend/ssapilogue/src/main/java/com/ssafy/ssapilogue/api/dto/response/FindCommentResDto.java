package com.ssafy.ssapilogue.api.dto.response;

import com.ssafy.ssapilogue.core.domain.ProjectComment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@ApiModel("FindCommentResDto")
public class FindCommentResDto {

    @ApiModelProperty(value = "댓글 아이디", example = "1")
    private Long commentId;

    @ApiModelProperty(name="댓글 내용", example="프로젝트 잘봤습니다!")
    private String content;

    @ApiModelProperty(value = "댓글 작성자 닉네임", example = "현서")
    private String nickname;

    @ApiModelProperty(value = "유저 사진 경로", example = "ftpServerUrl/pic.jpg")
    private String profileImage;

    @ApiModelProperty(name="댓글 작성일", example="2022-02-01-23:59")
    private String createdAt;

    public FindCommentResDto(ProjectComment projectComment) {
        commentId = projectComment.getId();
        content = projectComment.getContent();
        nickname = projectComment.getUser().getNickname();
        profileImage = projectComment.getUser().getImage();
    }
}
