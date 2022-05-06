package com.ssafy.ssapilogue.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name="user_info_split")
@NoArgsConstructor
public class UserInfoSplit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "split_id")
    private Long id;

    private String splitNickname;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    @Builder
    public UserInfoSplit(String splitNickname, UserInfo userInfo) {
        this.splitNickname = splitNickname;
        this.userInfo = userInfo;
    }
}
