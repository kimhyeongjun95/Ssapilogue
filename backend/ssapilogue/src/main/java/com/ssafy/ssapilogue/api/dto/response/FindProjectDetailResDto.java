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
@ApiModel("FindProjectDetailResDto")
public class FindProjectDetailResDto {

    @ApiModelProperty(value = "프로젝트 이름", example = "라이키")
    private String title;

    @ElementCollection
    @ApiModelProperty(value = "기술 스택", example = "['ReactNative', 'Spring']")
    private List<String> techStack;

//    @ApiModelProperty(value = "멤버 닉네임", example = "['동균', '현서']")
//    private List<String> member;

    @ApiModelProperty(value = "카테고리", example = "자율")
    private Category category;

    @ApiModelProperty(value = "배포 주소", example = "https://j6ssafy.c104.com/")
    private String deployAddress;

    @ApiModelProperty(value = "깃 주소", example = "https://gitlab.com/ssapilouge")
    private String gitAddress;

    @ApiModelProperty(value = "썸네일 이미지", example = "https://j6ssafy.c104.com/images/xxxxx")
    private String thumbnail;

    @ApiModelProperty(value = "리드미", example = "라이키 readme 어쩌구")
    private String readme;

//    @ApiModelProperty(value = "북마크 여부", example = "False")
//    private String isBookmarked;

//    @ApiModelProperty(value = "좋아요 여부", example = "False")
//    private String isLiked;

    public FindProjectDetailResDto(Project project) {
        title = project.getTitle();
        techStack = project.getTechStacks().stream().map(TechStack::getName).collect(Collectors.toList());
        category = project.getCategory();
        deployAddress = project.getDeployAddress();
        gitAddress = project.getGitAddress();
        thumbnail = project.getThumbnail();
        readme = project.getReadme();
    }
}
