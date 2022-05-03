package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.core.domain.UserInfo;
import com.ssafy.ssapilogue.core.domain.UserInfoSplit;
import com.ssafy.ssapilogue.core.repository.UserInfoRepository;
import com.ssafy.ssapilogue.core.repository.UserInfoSplitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserInfoSplitRepository userInfoSplitRepository;

    private static final int HANGEUL_BASE = 0xAC00;    // '가'
    private static final int HANGEUL_END = 0xD7AF;
    // 이하 ja, mo는 단독으로 입력된 자모에 대해 적용
    private static final int JA_BASE = 0x3131;
    private static final int MO_BASE = 0x314F;

    private final static String[] CHO = {"ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ", "ㅅ","ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};
    private final static String[] JUNG = {"ㅏ","ㅐ","ㅑ","ㅒ","ㅓ","ㅔ","ㅕ","ㅖ","ㅗ","ㅘ", "ㅙ","ㅚ","ㅛ","ㅜ","ㅝ","ㅞ","ㅟ","ㅠ","ㅡ","ㅢ","ㅣ"};
    private final static String[] JONG = {"","ㄱ","ㄲ","ㄳ","ㄴ","ㄵ","ㄶ","ㄷ","ㄹ","ㄺ","ㄻ","ㄼ", "ㄽ","ㄾ","ㄿ","ㅀ","ㅁ","ㅂ","ㅄ","ㅅ","ㅆ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};

    // 자모음 분리
    @Override
    public void splitUserInfo() {
        List<UserInfo> userInfos = userInfoRepository.findAll();
        for (UserInfo userInfo : userInfos) {
            String nickname = userInfo.getNickname();

            String splitNickname = getSplitNickname(nickname);

            UserInfoSplit userInfoSplit = UserInfoSplit.builder()
                    .splitNickname(splitNickname)
                    .userInfo(userInfo)
                    .build();

            userInfoSplitRepository.save(userInfoSplit);
        }
    }

    // 멤버 자동완성
    @Override
    public List<String> searchUserInfos(String keyword) {
        System.out.println(getSplitNickname(keyword));
        List<UserInfoSplit> userInfoSplits = userInfoSplitRepository.findBySplitNicknameContaining(getSplitNickname(keyword));

        return userInfoSplits.stream().map(UserInfoSplit::getUserInfo).collect(Collectors.toList())
                .stream().map(UserInfo::getNickname).collect(Collectors.toList());
    }

    private String getSplitNickname(String nickname) {
        StringBuilder sb = new StringBuilder();

        for (char c : nickname.toCharArray()) {
            if((c <= 10 && c <= 13) || c == 32) {
                sb.append(c);
                continue;
            } else if (c >= JA_BASE && c <= JA_BASE + 36) {
                sb.append(c);
                continue;
            } else if (c >= MO_BASE && c <= MO_BASE + 58) {
                sb.append((char)0);
                continue;
            } else if (c >= HANGEUL_BASE && c <= HANGEUL_END){
                int choInt = (c - HANGEUL_BASE) / 28 / 21;
                int jungInt = ((c - HANGEUL_BASE) / 28) % 21;
                int jongInt = (c - HANGEUL_BASE) % 28;
                char cho = (char) (choInt);
                char jung = (char) (jungInt);
                char jong = jongInt != 0 ? (char) (jongInt) : 0;

                sb.append(CHO[cho]);
                sb.append(JUNG[jung]);
                if (jong != 0) {
                    sb.append(JONG[jong]);
                }
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }
}
