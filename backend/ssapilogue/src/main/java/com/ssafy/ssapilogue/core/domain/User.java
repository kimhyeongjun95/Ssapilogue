package com.ssafy.ssapilogue.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@NoArgsConstructor
@Table(name="user")
public class User {

    @Id
    @NotBlank
    private String userId;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    private String gitId;

    private String greeting;

    private String image;

    private int likes;

    @Builder
    public User(String email, String password, String userId, String nickname, String gitId, String greeting, String image) {
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.nickname = nickname;
        this.gitId = gitId;
        this.greeting = greeting;
        this.image = image;
        this.likes = 0;
    }
}
