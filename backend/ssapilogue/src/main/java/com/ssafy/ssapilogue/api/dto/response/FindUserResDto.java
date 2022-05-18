package com.ssafy.ssapilogue.api.dto.response;

import com.ssafy.ssapilogue.core.domain.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@ApiModel("FindUserResDto")
public class FindUserResDto {

    @ApiModelProperty(value = "이메일", example = "test1234@naver.com")
    private String email;

    @ApiModelProperty(value = "유저네임", example = "test1234")
    private String username;

    @ApiModelProperty(value = "닉네임", example = "김싸피[광주_1반_C104]팀원")
    private String nickname;

    @ApiModelProperty(value = "깃헙 아이디", example = "test123")
    private String github;

    @ApiModelProperty(value = "자기소개", example = "안녕하세요 김싸피입니다.")
    private String greeting;

    @ApiModelProperty(value = "프로필 사진", example = "https://j6ssafy.c104.com/images/xxxxx")
    private String image;

    @ApiModelProperty(value = "좋아요", example = "100")
    private int userLiked;

    @ApiModelProperty(value = "포스팅한 프로젝트")
    private List<FindMyProjectResDto> projects;

    @ApiModelProperty(value = "북마크한 프로젝트")
    private List<FindMyProjectResDto> bookmarkList;

    public FindUserResDto(User user) {
        email = user.getEmail();
        username = user.getUsernameConv();
        nickname = user.getNickname();
        github = user.getGithub();
        greeting = user.getGreeting();
        image = user.getImage();
        userLiked = user.getLikes();
        List<Project> userProjects = user.getProjects();
        List<ProjectMember> projectMembers = user.getProjectMembers();
        for (int i=0; i < projectMembers.size(); i++) {
            if (projectMembers.get(i).getUser().getEmail().equals(email)) {
                if (userProjects.contains(projectMembers.get(i).getProject()) == false) {
                    userProjects.add(projectMembers.get(i).getProject());
                }
            }
        }
        projects = userProjects.stream().map(FindMyProjectResDto::new).collect(Collectors.toList());
        bookmarkList = user.getBookmarkList().stream().map(bookmark -> new FindMyProjectResDto(bookmark.getProject())).collect(Collectors.toList());
    }
}
