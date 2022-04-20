package com.ssafy.ssapilogue.api.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@ApiModel("SignupUserReqDto")
public class SignupUserReqDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String mmId;

    @NotBlank
    private String nickname;

    private String gitId;
}
