package com.ssafy.ssapilogue.api.dto.response;

import com.ssafy.ssapilogue.core.domain.Bookmark;
import com.ssafy.ssapilogue.core.domain.Project;
import com.ssafy.ssapilogue.core.domain.User;
import com.ssafy.ssapilogue.core.domain.UserIdentity;
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
        nickname = user.getNickname();
        github = user.getGithub();
        greeting = user.getGreeting();
        image = user.getImage();
        userLiked = user.getLikes();
        projects = user.getProjects().stream().map(FindMyProjectResDto::new).collect(Collectors.toList());
        bookmarkList = user.getBookmarkList().stream().map(bookmark -> new FindMyProjectResDto(bookmark.getProject())).collect(Collectors.toList());
    }
}
