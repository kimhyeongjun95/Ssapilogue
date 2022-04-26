package com.ssafy.ssapilogue.api.dto.response;

import com.ssafy.ssapilogue.core.domain.User;
import com.ssafy.ssapilogue.core.domain.UserIdentity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("SignupUserResDto")
public class SignupUserResDto {

    @ApiModelProperty(value = "이메일", example = "test1234@naver.com")
    private String email;

    @ApiModelProperty(value = "닉네임", example = "김싸피[광주_1반_C104]팀원")
    private String nickname;

    @ApiModelProperty(value = "깃헙 아이디", example = "test123")
    private String github;

    @ApiModelProperty(value = "역할", example = "ROLE_USER")
    private UserIdentity userIdentity;

    public SignupUserResDto(User user) {
        email = user.getEmail();
        nickname = user.getNickname();
        github = user.getGithub();
        userIdentity = user.getUserIdentity();
    }
}
