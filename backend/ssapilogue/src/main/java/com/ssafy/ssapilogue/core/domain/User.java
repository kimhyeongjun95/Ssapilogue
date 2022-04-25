package com.ssafy.ssapilogue.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name="user")
public class User implements UserDetails {

    @Id
    @NotBlank
    private String userId;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    @NotBlank
    private String username;

    private String github;

    private String greeting;

    private String image;

    private int likes;

    @Enumerated(EnumType.STRING)
    private UserIdentity userIdentity;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProjectComment> projectComments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProjectMember> projectMembers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Liked> likedList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @Builder
    public User(String email, String password, String userId, String nickname, String username, String github, String greeting, String image) {
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.nickname = nickname;
        this.username = username;
        this.github = github;
        this.greeting = greeting;
        this.image = image;
        this.likes = 0;
        this.userIdentity = UserIdentity.ROLE_USER;
    }

    public void update(String github, String greeting) {
        this.github = github;
        this.greeting = greeting;
    }

    public void updateImg(String image) {
        this.image = image;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        UserIdentity userIdentity = getUserIdentity();
        if(userIdentity == UserIdentity.ROLE_UNAUTH) {
            authorities.add(new SimpleGrantedAuthority("ROLE_UNAUTH"));
        } else if (userIdentity == UserIdentity.ROLE_USER) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else if (userIdentity == UserIdentity.ROLE_ADMIN) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
