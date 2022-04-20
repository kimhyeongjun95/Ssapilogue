package com.ssafy.ssapilogue.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String mmId;

    @NotBlank
    private String nickname;

    private String gitId;

    @Builder
    public User(String email, String password, String mmId, String nickname, String gitId) {
        this.email = email;
        this.password = password;
        this.mmId = mmId;
        this.nickname = nickname;
        this.gitId = gitId;
    }
}
