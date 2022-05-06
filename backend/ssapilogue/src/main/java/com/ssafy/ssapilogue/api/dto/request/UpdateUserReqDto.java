package com.ssafy.ssapilogue.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("UpdateUserReqDto")
public class UpdateUserReqDto {

    @ApiModelProperty(value = "깃헙 아이디", example = "test123")
    private String github;

    @ApiModelProperty(value = "자기소개", example = "안녕하세요 김싸피입니다.")
    private String greeting;

    @ApiModelProperty(value = "프로필 사진", example = "https://j6ssafy.c104.com/images/xxxxx")
    private String image;
}
