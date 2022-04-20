package com.ssafy.ssapilogue.api.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@ApiModel("LoginUserReqDto")
public class LoginUserReqDto {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
