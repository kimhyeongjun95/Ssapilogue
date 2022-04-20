package com.ssafy.ssapilogue.api.dto.response;

import com.ssafy.ssapilogue.core.domain.User;

public class SignupUserResDto {

    private String email;

    private String nickname;

    private String gitId;

    public SignupUserResDto(User user) {
        email = user.getEmail();
        nickname = user.getNickname();
        gitId = user.getGitId();
    }
}
