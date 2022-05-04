package com.ssafy.ssapilogue.api.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("UpdateUserReqDto")
public class UpdateUserReqDto {

    private String github;

    private String greeting;

    private String image;
}
