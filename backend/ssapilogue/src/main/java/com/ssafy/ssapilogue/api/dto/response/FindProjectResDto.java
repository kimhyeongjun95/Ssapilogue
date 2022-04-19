package com.ssafy.ssapilogue.api.dto.response;

import com.ssafy.ssapilogue.core.domain.Category;
import com.ssafy.ssapilogue.core.domain.Project;
import com.ssafy.ssapilogue.core.domain.TechStack;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@ApiModel("FindProjectResDto")
public class FindProjectResDto {

    @ApiModelProperty(value = "프로젝트 이름", example = "라이키")
    private String title;

    @ApiModelProperty(value = "프로젝트 소개", example = "자전거 프로젝트입니다!")
    private String introduce;

    @ApiModelProperty(value = "카테고리", example = "자율")
    private Category category;

    @ElementCollection
    @ApiModelProperty(value = "기술 스택", example = "['ReactNative', 'Spring']")
    private List<String> techStack;

    @ApiModelProperty(value = "썸네일 이미지", example = "https://j6ssafy.c104.com/images/xxxxx")
    private String thumbnail;

//    @ApiModelProperty(value = "북마크 여부", example = "False")
//    private String isBookmarked;

    @ApiModelProperty(value = "조회수", example = "100")
    private int hits;

//    @ApiModelProperty(value = "좋아요 수", example = "50")
//    private String likeCnt;
//
//    @ApiModelProperty(value = "댓글 수", example = "20")
//    private String commentCnt;

    public FindProjectResDto(Project project) {
        title = project.getTitle();
        introduce = project.getIntroduce();
        category = project.getCategory();
        techStack = project.getTechStacks().stream().map(TechStack::getName).collect(Collectors.toList());
        thumbnail = project.getThumbnail();
        hits = project.getHits();
    }
}
