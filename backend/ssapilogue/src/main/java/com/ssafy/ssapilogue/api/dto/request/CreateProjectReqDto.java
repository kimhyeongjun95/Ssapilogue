package com.ssafy.ssapilogue.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@ApiModel("CreateProjectReqDto")
public class CreateProjectReqDto {

    @NotNull
    @ApiModelProperty(value = "프로젝트 이름", required = true, example = "라이키")
    private String title;

    @NotNull
    @ApiModelProperty(value = "프로젝트 소개", required = true, example = "자전거 프로젝트입니다!")
    private String introduce;

    @NotNull
    @ApiModelProperty(value = "카테고리", required = true, example = "자율")
    private String category;

    @NotNull
    @ElementCollection
    @ApiModelProperty(value = "기술 스택", example = "['ReactNative', 'Spring']")
    private List<String> techStack;

    @ElementCollection
    @ApiModelProperty(value = "멤버", example = "['동균', '현서']")
    private List<String> member;

    @ApiModelProperty(value = "배포 주소", example = "https://j6ssafy.c104.com/")
    private String deployAddress;

    @NotNull
    @ApiModelProperty(value = "깃 주소", required = true, example = "https://gitlab.com/ssapilouge")
    private String gitAddress;

    @ApiModelProperty(value = "썸네일 이미지", example = "https://j6ssafy.c104.com/images/xxxxx")
    private String thumbnail;

    @NotNull
    @ApiModelProperty(value = "리드미 여부", required = true, example = "1")
    private int readmeCheck;

    @ApiModelProperty(value = "리드미", example = "라이키 readme 어쩌구")
    private String readme;
}
