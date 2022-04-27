package com.ssafy.ssapilogue.api.dto.response;

import com.ssafy.ssapilogue.core.domain.User;
import com.ssafy.ssapilogue.core.domain.UserIdentity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("FindUserResDto")
public class FindUserResDto {

    private String email;

    private String nickname;

    private String github;

    private String greeting;

    private String image;

    public FindUserResDto(User user) {
        email = user.getEmail();
        nickname = user.getNickname();
        github = user.getGithub();
        greeting = user.getGreeting();
        image = user.getImage();
    }
}
