package com.ssafy.ssapilogue.api.dto.response;

import com.ssafy.ssapilogue.core.domain.Review;
import com.ssafy.ssapilogue.core.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("FindSubjectiveReviewResDto")
public class FindSubjectiveReviewResDto {

    @ApiModelProperty(value = "리뷰 내용", example = "디자인이 너무 깔끔해요~")
    private String content;

    @ApiModelProperty(value = "리뷰 작성자 닉네임", example = "동균")
    private String nickname;

    @ApiModelProperty(value = "유저 사진 경로", example = "ftpServerUrl/pic.jpg")
    private String profileImage;

    @ApiModelProperty(value = "리뷰 작성일", example = "2022-02-01 23:59")
    private String createAt;

    public FindSubjectiveReviewResDto(Review review, String nickname, String profileImage, String createAt) {
        content = review.getContent();
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.createAt = createAt;
    }
}
