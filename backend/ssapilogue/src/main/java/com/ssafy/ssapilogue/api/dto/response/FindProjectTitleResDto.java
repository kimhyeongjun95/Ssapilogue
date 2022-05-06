package com.ssafy.ssapilogue.api.dto.response;

import com.ssafy.ssapilogue.core.domain.Project;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("FindProjectTitleResDto")
public class FindProjectTitleResDto {

    @ApiModelProperty(value = "프로젝트 아이디", example = "1")
    private Long projectId;

    @ApiModelProperty(value = "프로젝트 이름", example = "라이키")
    private String title;

    public FindProjectTitleResDto(Project project) {
        projectId = project.getId();
        title = project.getTitle();
    }
}
