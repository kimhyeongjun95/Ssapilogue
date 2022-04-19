package com.ssafy.ssapilogue.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private Category category;

    private String deployAddress;

    private String gitAddress;

    private String thumbnail;

    private String readme;

    private int hits;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    private Project(String title, String introduce, Category category, String deployAddress, String gitAddress, String thumbnail, String readme) {
        this.title = title;
        this.introduce = introduce;
        this.category = category;
        this.deployAddress = deployAddress;
        this.gitAddress = gitAddress;
        this.thumbnail = thumbnail;
        this.readme = readme;
    }

    // 조회수 증가를 위한 편의 함수
    public void increaseHits() {
        this.hits++;
    }
}
