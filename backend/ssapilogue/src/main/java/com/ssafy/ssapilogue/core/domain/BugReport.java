package com.ssafy.ssapilogue.core.domain;

import com.ssafy.ssapilogue.api.dto.request.CreateBugReportReqDto;
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
@Table(name = "bug_report")
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class})
public class BugReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bug_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private Boolean isSolved;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "bugReport", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<BugReportComment> bugReportComments = new ArrayList<>();

    @Builder
    public BugReport(Project project, User user, String title, String content, Boolean isSolved) {
        this.project = project;
        this.user = user;
        this.title = title;
        this.content = content;
        this.isSolved = isSolved;
    }

    public void update(CreateBugReportReqDto createBugReportReqDto) {
        this.title = createBugReportReqDto.getTitle();
        this.content = createBugReportReqDto.getContent();
    }

    public void updateSolved(Boolean update) {
        this.isSolved = update;
    }
}
