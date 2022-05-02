package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.LoginUserReqDto;
import com.ssafy.ssapilogue.api.dto.request.SignupUserReqDto;
import com.ssafy.ssapilogue.api.dto.request.UpdateUserReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindUserResDto;
import com.ssafy.ssapilogue.api.dto.response.LoginUserResDto;
import com.ssafy.ssapilogue.api.dto.response.SignupUserResDto;
import com.ssafy.ssapilogue.core.domain.User;
import com.ssafy.ssapilogue.core.domain.UserInfo;
import com.ssafy.ssapilogue.core.repository.UserInfoRepository;
import com.ssafy.ssapilogue.core.repository.UserRepository;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final UserInfoRepository userInfoRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private static PasswordEncoder passwordEncoder;

    @Value("${profileImg.path}")
    private String uploadFolder;

    @Transactional
    @Override
    public User signup(SignupUserReqDto signupUserReqDto) {
        UserInfo userInfo = userInfoRepository.findByUserId(signupUserReqDto.getUserId());
        String password = signupUserReqDto.getPassword();

        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encPass = passwordEncoder.encode(password);
//        String encPass = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = User.builder()
                .email(signupUserReqDto.getEmail())
                .password(encPass)
                .userId(signupUserReqDto.getUserId())
                .nickname(userInfo.getNickname())
                .username(userInfo.getUsername())
                .github(signupUserReqDto.getGithub())
                .greeting(signupUserReqDto.getGreeting())
                .image(signupUserReqDto.getImage())
                .build();
        User save = userRepository.save(user);

        return user;
    }

    @Override
    public User login(LoginUserReqDto loginUserReqDto) throws Exception {
        User findUser = userRepository.findByEmail(loginUserReqDto.getEmail());
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        if (findUser == null) throw new Exception("멤버가 조회되지않습니다.");
        if (findUser != null) {
            boolean check = passwordEncoder.matches(loginUserReqDto.getPassword(), findUser.getPassword());
            if (check == false) throw new Exception("비밀번호가 틀립니다.");
        }
        return findUser;
    }

    @Override
    @Transactional
    public void updateUser(UpdateUserReqDto updateUserReqDto) {
        User user = userRepository.findByEmail(updateUserReqDto.getEmail());
        user.update(updateUserReqDto);
    }

    @Override
    public void deleteUser(String email) {
        User findUser = userRepository.findByEmail(email);
        userRepository.delete(findUser);
    }

    @Override
    public FindUserResDto findUserProfile(String email) {
        User findUser = userRepository.findByEmail(email);
        return new FindUserResDto(findUser);
    }

    @Override
    public String updateImage(String email, MultipartFile multipartFile) {
        User user = userRepository.findByEmail(email);
        String imageFileName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        if(multipartFile.getSize() != 0) {
            try {
                Files.write(imageFilePath, multipartFile.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "http://k6c104.p.ssafy.io/images/profileImg/" + imageFileName;
    }

    @Override
    public String issueNewToken(String refreshToken) throws Exception {
        String userEmail = jwtTokenProvider.getUserEmail(refreshToken);
        User findUser = userRepository.findByEmail(userEmail);
        if (!findUser.getRefreshToken().equals(refreshToken)) {
            throw new Exception("유효하지 않은 Refresh Token 입니다.");
        }
        String newToken = jwtTokenProvider.createToken(findUser.getEmail(), findUser.getUserIdentity());
        return "Bearer " + newToken;
    }

}
