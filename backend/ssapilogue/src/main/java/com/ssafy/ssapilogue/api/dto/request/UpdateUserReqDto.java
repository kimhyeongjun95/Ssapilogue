package com.ssafy.ssapilogue.api.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("UpdateUserReqDto")
public class UpdateUserReqDto {

    private String email;

    private String github;

    private String greeting;

    private String image;
}
