package com.ssafy.ssapilogue.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("LoginUserReqDto")
public class LoginUserReqDto {

    @NotNull
    @ApiModelProperty(value = "이메일", required = true, example = "test1234@naver.com")
    private String email;

    @NotNull
    @ApiModelProperty(value = "비밀번호", required = true)
    private String password;

    @NotNull
    @ApiModelProperty(value = "유저 mm 아이디", required = true)
    private String userId;

}
