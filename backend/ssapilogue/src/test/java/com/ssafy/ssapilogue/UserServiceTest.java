package com.ssafy.ssapilogue;

import com.ssafy.ssapilogue.api.dto.request.LoginUserReqDto;
import com.ssafy.ssapilogue.api.dto.request.SignupUserReqDto;
import com.ssafy.ssapilogue.api.dto.request.UpdateUserReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindUserResDto;
import com.ssafy.ssapilogue.api.service.UserService;
import com.ssafy.ssapilogue.core.domain.User;
import com.ssafy.ssapilogue.core.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private static PasswordEncoder passwordEncoder;

    @Before
    public void before() {
        createUser();
    }

    private User savedUser;

    @Test
    public void updateUserTest() {
        UpdateUserReqDto updateUserReqDto = new UpdateUserReqDto("test", "안녕하세요 김싸피입니다", "http://k6c104.p.ssafy.io/profileImg");
        userService.updateUser(savedUser, updateUserReqDto);
        User findUser = userRepository.findByEmail(savedUser.getEmail());
        Assert.assertEquals(findUser.getGithub(), updateUserReqDto.getGithub());
    }

    @Test
    public void deleteUserTest() {
        userService.deleteUser(savedUser.getEmail());
        User findUser = userRepository.findByEmail(savedUser.getEmail());
        Assert.assertEquals(findUser, null);
    }

    @Test
    public void findUserProfileTest() {
        FindUserResDto userProfile = userService.findUserProfile(savedUser.getEmail());
        User findUser = userRepository.findByEmail(savedUser.getEmail());
        Assert.assertEquals(userProfile.getGithub(), findUser.getGithub());
    }

    private void createUser() {
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        User user = User.builder()
                .userId("12345678")
                .email("test1234@naver.com")
                .password(passwordEncoder.encode("password"))
                .github("test1234")
                .greeting("안녕하세요")
                .nickname("[광주_1반]김싸피")
                .username("test1234")
                .image("http://k6c104.p.ssafy.io/profileImg")
                .build();
        savedUser = userRepository.save(user);
    }

}
