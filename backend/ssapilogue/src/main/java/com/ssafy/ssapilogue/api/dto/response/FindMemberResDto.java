package com.ssafy.ssapilogue.api.dto.response;

import com.ssafy.ssapilogue.core.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("FindMemberResDto")
public class FindMemberResDto {

    @ApiModelProperty(value = "멤버 username", example = "khyunchoi")
    private String username;

    @ApiModelProperty(value = "멤버 닉네임", example = "동균")
    private String nickname;

    public FindMemberResDto(User user) {
        username = user.getUsernameConv();
        nickname = user.getNickname();
    }
}
