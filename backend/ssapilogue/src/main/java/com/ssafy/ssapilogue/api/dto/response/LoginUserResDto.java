package com.ssafy.ssapilogue.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("LoginUserResDto")
public class LoginUserResDto {

    @ApiModelProperty(value = "이메일", example = "test1234@naver.com")
    private String email;

    @ApiModelProperty(value = "토큰")
    private String token;

    @ApiModelProperty(value = "리프레쉬 토큰")
    private String refreshToken;

    public LoginUserResDto(String email, String token, String refreshToken) {
        this.email = email;
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
