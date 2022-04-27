package com.ssafy.ssapilogue.core.domain;

import com.ssafy.ssapilogue.api.dto.request.CreateProjectReqDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "project")
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class})
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    private String title;

    private String introduce;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String deployAddress;

    private String gitAddress;

    private String thumbnail;

    @Column(columnDefinition = "LONGTEXT")
    private String readme;

    private int hits;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProjectStack> projectStacks = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProjectMember> projectMembers = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<AnonymousMember> anonymousMembers = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProjectComment> projectComments = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Liked> likedList = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private Project(String title, String introduce, Category category, String deployAddress, String gitAddress, String thumbnail, String readme, User user) {
        this.title = title;
        this.introduce = introduce;
        this.category = category;
        this.deployAddress = deployAddress;
        this.gitAddress = gitAddress;
        this.thumbnail = thumbnail;
        this.readme = readme;
        this.user = user;
    }

    // 프로젝트 이미지 수정을 위한 편의 함수
    public void updateImg(String image) {
        this.thumbnail = image;
    }

    // 프로젝트 리드미 수정을 위한 편의 함수
    public void updateReadme(String readmeContent) {
        this.readme = readmeContent;
    }

    // 조회수 증가를 위한 편의 함수
    public void increaseHits() {
        this.hits++;
    }

    // 프로젝트 수정을 위한 편의 함수
    public void update(CreateProjectReqDto createProjectReqDto) {
        this.title = createProjectReqDto.getTitle();
        this.introduce = createProjectReqDto.getIntroduce();
        this.category = Category.valueOf(createProjectReqDto.getCategory());
        this.deployAddress = createProjectReqDto.getDeployAddress();
        this.gitAddress = createProjectReqDto.getGitAddress();
        this.thumbnail = createProjectReqDto.getThumbnail();
        this.readme = createProjectReqDto.getReadme();
    }
}
