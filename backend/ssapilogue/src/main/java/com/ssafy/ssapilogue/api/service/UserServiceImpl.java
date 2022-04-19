package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.UserLoginDto;
import com.ssafy.ssapilogue.api.dto.request.UserRequestDto;
import com.ssafy.ssapilogue.api.dto.response.UserSimpleResponseDto;
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
    public UserSimpleResponseDto signup(UserRequestDto userDto) {
        String password = userDto.getPassword();
        String encPass = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = User.builder()
                .email(userDto.getEmail())
                .password(encPass)
                .mmId(userDto.getMmId())
                .nickname(userDto.getNickname())
                .gitId(userDto.getGitId())
                .build();
        user = userRepository.save(user);

        return new UserSimpleResponseDto(user);
    }

    @Override
    public User login(UserLoginDto userLoginDto) throws Exception {
        User findUser = userRepository.findByEmail(userLoginDto.getEmail());
        if (findUser == null) throw new Exception("멤버가 조회되지않습니다.");
        boolean check = BCrypt.checkpw(userLoginDto.getPassword(), findUser.getPassword());
        if (check == false) throw new Exception("비밀번호가 틀립니다.");
        return findUser;
    }
}
