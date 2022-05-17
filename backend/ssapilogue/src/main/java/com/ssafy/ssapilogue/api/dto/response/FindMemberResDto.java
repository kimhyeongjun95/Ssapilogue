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

    @ApiModelProperty(value = "멤버 id", example = "xxxxxxxxx")
    private String userId;

    @ApiModelProperty(value = "멤버 닉네임", example = "동균")
    private String nickname;

    public FindMemberResDto(User user) {
        userId = user.getUserId();
        nickname = user.getNickname();
    }
}
