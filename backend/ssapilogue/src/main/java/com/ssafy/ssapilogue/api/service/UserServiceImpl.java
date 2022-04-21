package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.LoginUserReqDto;
import com.ssafy.ssapilogue.api.dto.request.SignupUserReqDto;
import com.ssafy.ssapilogue.api.dto.response.SignupUserResDto;
import com.ssafy.ssapilogue.core.domain.User;
import com.ssafy.ssapilogue.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Transactional
    @Override
    public SignupUserResDto signup(SignupUserReqDto signupUserReqDto) {
        String password = signupUserReqDto.getPassword();
        String encPass = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = User.builder()
                .email(signupUserReqDto.getEmail())
                .password(encPass)
                .userId(signupUserReqDto.getUserId())
                .nickname(signupUserReqDto.getNickname())
                .gitId(signupUserReqDto.getGitId())
                .greeting(signupUserReqDto.getGreeting())
                .image(signupUserReqDto.getImage())
                .build();
        user = userRepository.save(user);

        return new SignupUserResDto(user);
    }

    @Override
    public User login(LoginUserReqDto loginUserReqDto) throws Exception {
        User findUser = userRepository.findByEmail(loginUserReqDto.getEmail());
        if (findUser == null) throw new Exception("멤버가 조회되지않습니다.");
        boolean check = BCrypt.checkpw(loginUserReqDto.getPassword(), findUser.getPassword());
        if (check == false) throw new Exception("비밀번호가 틀립니다.");
        return findUser;
    }
}
