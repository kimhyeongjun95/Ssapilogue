package com.ssafy.ssapilogue.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name="user_info")
@NoArgsConstructor
public class UserInfo {

    @Id
    @NotNull
    private String userId;

    @NotNull
    private String nickname;

    @NotNull
    private String username;

    @Builder
    public UserInfo(String userId, String nickname, String username) {
        this.userId = userId;
        this.nickname = nickname;
        this.username = username;
    }
}
