package com.ssafy.ssapilogue.api.dto.response;

import com.ssafy.ssapilogue.core.domain.*;
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

    @ApiModelProperty(value = "프로젝트 아이디", example = "1")
    private Long projectId;

    @ApiModelProperty(value = "프로젝트 이름", example = "라이키")
    private String title;

    @ApiModelProperty(value = "프로젝트 소개", example = "라이키 프로젝트입니다.")
    private String introduce;

    @ApiModelProperty(value = "작성자 이메일", example = "khyunchoi@naver.com")
    private String email;

    @ElementCollection
    @ApiModelProperty(value = "기술 스택", example = "['ReactNative', 'Spring']")
    private List<String> techStack;

    @ApiModelProperty(value = "멤버 닉네임", example = "['동균', '현서']")
    private List<String> member;

    @ApiModelProperty(value = "멤버 닉네임", example = "['형준', '은서']")
    private List<String> anonymousMember;

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

    @ApiModelProperty(value = "북마크 여부", example = "False")
    private Boolean isBookmarked;

    @ApiModelProperty(value = "좋아요 여부", example = "False")
    private Boolean isLiked;

    @ElementCollection
    @ApiModelProperty(value = "다른 프로젝트 리스트")
    private List<FindProjectResDto> anotherProjects;

    @ApiModelProperty(value = "댓글 수", example = "20")
    private int commentCnt;

    @ElementCollection
    @ApiModelProperty(value = "댓글 리스트")
    private List<FindCommentResDto> comment;

    public FindProjectDetailResDto(Project project, Boolean isLiked, Boolean isBookmarked, List<FindProjectResDto> findProjectResDtos, List<FindCommentResDto> commentList) {
        projectId = project.getId();
        title = project.getTitle();
        introduce = project.getIntroduce();
        email = project.getUser().getEmail();
        techStack = project.getProjectStacks()
                .stream().map(ProjectStack::getTechStack).collect(Collectors.toList())
                .stream().map(TechStack::getName).collect(Collectors.toList());
        member = project.getProjectMembers()
                .stream().map(ProjectMember::getUser).collect(Collectors.toList())
                .stream().map(User::getNickname).collect(Collectors.toList());
        anonymousMember = project.getAnonymousMembers()
                .stream().map(AnonymousMember::getNickname).collect(Collectors.toList());
        category = project.getCategory();
        deployAddress = project.getDeployAddress();
        gitAddress = project.getGitAddress();
        thumbnail = project.getThumbnail();
        readme = project.getReadme();
        this.isBookmarked = isBookmarked;
        this.isLiked = isLiked;
        anotherProjects = findProjectResDtos;
        commentCnt = commentList.size();
        comment = commentList;
    }
}
