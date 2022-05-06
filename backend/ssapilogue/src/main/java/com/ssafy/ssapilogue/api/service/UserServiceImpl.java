package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.api.dto.request.LoginUserReqDto;
import com.ssafy.ssapilogue.api.dto.request.SignupUserReqDto;
import com.ssafy.ssapilogue.api.dto.request.UpdateUserReqDto;
import com.ssafy.ssapilogue.api.dto.response.FindUserResDto;
import com.ssafy.ssapilogue.api.dto.response.LoginUserResDto;
import com.ssafy.ssapilogue.api.dto.response.SignupUserResDto;
import com.ssafy.ssapilogue.api.exception.CustomException;
import com.ssafy.ssapilogue.api.exception.ErrorCode;
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
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final UserInfoRepository userInfoRepository;

    private final UserInfoService userInfoService;

    private final JwtTokenProvider jwtTokenProvider;

    private static PasswordEncoder passwordEncoder;

    @Value("${profileImg.path}")
    private String uploadFolder;

    @Override
    public User signup(SignupUserReqDto signupUserReqDto) {
        String encodeMmId = userInfoService.encrypt(signupUserReqDto.getUserId().getBytes());

        UserInfo userInfo = userInfoRepository.findByUserId(encodeMmId);
        if (userInfo == null) {
            throw new CustomException(ErrorCode.INVALID_USER);
        }

        String password = signupUserReqDto.getPassword();
        if (password == null) throw new CustomException(ErrorCode.NULL_PASSWORD);

        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encPass = passwordEncoder.encode(password);

        User user = User.builder()
                .email(signupUserReqDto.getEmail())
                .password(encPass)
                .userId(encodeMmId)
                .nickname(userInfo.getNickname())
                .username(userInfo.getUsername())
                .github(signupUserReqDto.getGithub())
                .greeting(signupUserReqDto.getGreeting())
                .image(signupUserReqDto.getImage())
                .build();

        User save = userRepository.save(user);

        return save;
    }

    @Override
    public User login(LoginUserReqDto loginUserReqDto) {
        String encodeMmId = userInfoService.encrypt(loginUserReqDto.getUserId().getBytes());
        if (userInfoRepository.findByUserId(encodeMmId) == null) {
            throw new CustomException(ErrorCode.INVALID_USER);
        }

        User findUser = userRepository.findByEmail(loginUserReqDto.getEmail());
        if (findUser == null) throw new CustomException(ErrorCode.NO_USER);

        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        boolean check = passwordEncoder.matches(loginUserReqDto.getPassword(), findUser.getPassword());
        if (check == false) throw new CustomException(ErrorCode.WRONG_PASSWORD);

        return findUser;
    }

    @Override
    public void updateUser(User user, UpdateUserReqDto updateUserReqDto) {
        user.update(updateUserReqDto);
    }

    @Override
    public void deleteUser(String email) {
        User findUser = userRepository.findByEmail(email);
        if (findUser == null) throw new CustomException(ErrorCode.NO_USER);
        userRepository.delete(findUser);
    }

    @Override
    public FindUserResDto findUserProfile(String email) {
        User findUser = userRepository.findByEmail(email);
        if (findUser == null) throw new CustomException(ErrorCode.NO_USER);
        return new FindUserResDto(findUser);
    }

    @Override
    public String updateImage(String email, MultipartFile multipartFile) {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new CustomException(ErrorCode.NO_USER);

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
        if (userEmail == null) throw new CustomException(ErrorCode.WRONG_REFRESH_TOKEN);

        User findUser = userRepository.findByEmail(userEmail);
        if (findUser == null) throw new CustomException(ErrorCode.NO_USER);

        if (!findUser.getRefreshToken().equals(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        String newToken = jwtTokenProvider.createToken(findUser.getEmail(), findUser.getUserIdentity());
        return "Bearer " + newToken;
    }

}
