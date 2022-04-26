package com.ssafy.ssapilogue.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@ApiModel("SignupUserReqDto")
public class SignupUserReqDto {

    @NotNull
    @ApiModelProperty(value = "이메일", required = true, example = "test1234@naver.com")
    private String email;

    @NotNull
    @ApiModelProperty(value = "비밀번호", required = true)
    private String password;

    @NotNull
    @ApiModelProperty(value = "유저 mm 아이디", required = true)
    private String userId;

    @ApiModelProperty(value = "깃헙 아이디", example = "test123")
    private String github;

    @ApiModelProperty(value = "자기소개", example = "안녕하세요 김싸피입니다.")
    private String greeting;

    @ApiModelProperty(value = "프로필 사진", example = "https://j6ssafy.c104.com/images/xxxxx")
    private String image;

}
