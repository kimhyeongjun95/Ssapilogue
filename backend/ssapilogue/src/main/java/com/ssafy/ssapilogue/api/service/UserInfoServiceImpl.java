package com.ssafy.ssapilogue.api.service;

import com.ssafy.ssapilogue.core.domain.UserInfo;
import com.ssafy.ssapilogue.core.domain.UserInfoSplit;
import com.ssafy.ssapilogue.core.repository.UserInfoRepository;
import com.ssafy.ssapilogue.core.repository.UserInfoSplitRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;
import java.util.List;
import java.util.NoSuchElementException;
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

    private static final String[] CHO = {"ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ", "ㅅ","ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};
    private static final String[] JUNG = {"ㅏ","ㅐ","ㅑ","ㅒ","ㅓ","ㅔ","ㅕ","ㅖ","ㅗ","ㅘ", "ㅙ","ㅚ","ㅛ","ㅜ","ㅝ","ㅞ","ㅟ","ㅠ","ㅡ","ㅢ","ㅣ"};
    private static final String[] JONG = {"","ㄱ","ㄲ","ㄳ","ㄴ","ㄵ","ㄶ","ㄷ","ㄹ","ㄺ","ㄻ","ㄼ", "ㄽ","ㄾ","ㄿ","ㅀ","ㅁ","ㅂ","ㅄ","ㅅ","ㅆ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};

    @Value("${encrypt.keyString}")
    private String keyString;

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

    @Override
    public void getUserInfo() throws ParseException {
        List<String> userIdList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            URI uri = UriComponentsBuilder
                    .fromUriString("https://meeting.ssafy.com")
                    .path("/api/v4/channels/tu7uoyka9jfx3cthbo9uscospo/members")
                    .queryParam("per_page", 200)
                    .queryParam("page", i)
                    .encode()
                    .build()
                    .toUri();

            RestTemplate restTemplate = new RestTemplate();

            // 토큰 새로 발급받아서 수정하기
            RequestEntity<Void> req = RequestEntity
                    .get(uri)
                    .header("authorization", "Bearer wzn76seko3fq789ajhfgrhkkhe")
                    .build();

            ResponseEntity<String> result = restTemplate.exchange(req, String.class);

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(result.getBody());
            JSONArray jsonArray = (JSONArray) obj;

            for (int j = 0; j < jsonArray.size(); j++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(j);
                userIdList.add(jsonObject.get("user_id").toString());
            }
        }

        for (int k = 0; k < userIdList.size(); k++) {
            URI uri = UriComponentsBuilder
                    .fromUriString("https://meeting.ssafy.com")
                    .path("/api/v4/users/")
                    .path(userIdList.get(k))
                    .encode()
                    .build()
                    .toUri();

            RestTemplate userRestTemplate = new RestTemplate();

            // 토큰 새로 발급받아서 수정하기
            RequestEntity<Void> userReq = RequestEntity
                    .get(uri)
                    .header("authorization", "Bearer wzn76seko3fq789ajhfgrhkkhe")
                    .build();

            ResponseEntity<String> result = userRestTemplate.exchange(userReq, String.class);

            JSONParser jsonParser = new JSONParser();
            Object object = jsonParser.parse(result.getBody());
            JSONObject jsonObject = (JSONObject) object;

            String encodeMmId = encrypt(jsonObject.get("id").toString().getBytes());
            UserInfo userInfo = UserInfo.builder()
                    .userId(encodeMmId)
                    .nickname(jsonObject.get("nickname").toString())
                    .username(jsonObject.get("username").toString())
                    .build();

            if (userInfoRepository.findByUserId(encodeMmId) == null) {
                userInfoRepository.save(userInfo);
            }
        }
    }

    @Override
    public String encrypt(byte[] mmId) {
        byte[] key = getKey();
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");

        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("nosuchalgorithmexception");
            return null;
        } catch (NoSuchPaddingException e) {
            System.out.println("nosuchpaddingexception");
            return null;
        }

        try {
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        } catch (InvalidKeyException e) {
            System.out.println("invalidkeyexception");
            e.printStackTrace();
            return null;
        }

        try {
            Encoder encoder = Base64.getEncoder();
            return new String(encoder.encode(cipher.doFinal(mmId)));
        } catch (IllegalBlockSizeException e) {
            System.out.println("illegalblocksizeexception");
        } catch (BadPaddingException e) {
            System.out.println("badpaddingexception");
        }
        return null;
    }

    @Override
    public String decrypt(byte[] mmId) {
        byte[] key = getKey();
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");

        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (NoSuchPaddingException e) {
            return null;
        }

        try {
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
        } catch (InvalidKeyException e) {
            return null;
        }

        try {
            Decoder decoder = Base64.getDecoder();
            return new String(cipher.doFinal(decoder.decode(mmId)));
        } catch (IllegalBlockSizeException e) {
        } catch (BadPaddingException e) {
        }
        return null;
    }

    private byte[] getKey() {
        return keyString.getBytes();
    }

}
