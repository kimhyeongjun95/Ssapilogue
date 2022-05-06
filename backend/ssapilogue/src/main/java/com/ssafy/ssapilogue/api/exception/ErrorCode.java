package com.ssafy.ssapilogue.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NO_USER(BAD_REQUEST, "존재하지 않는 유저입니다. 회원가입을 진행해주세요."),
    NULL_PASSWORD(BAD_REQUEST, "비밀번호를 입력해주세요."),
    ALREADY_EXIST_USER(BAD_REQUEST, "이미 가입한 회원입니다."),
    NO_TOKEN(BAD_REQUEST, "header에 토큰이 존재하지 않습니다."),
    NO_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 존재하지 않습니다."),

    INVALID_USER(UNAUTHORIZED, "ssapilogue를 이용할 수 없습니다."),
    WRONG_PASSWORD(UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    EXPIRED_TOKEN(UNAUTHORIZED, "만료된 토큰입니다."),
    WRONG_TOKEN(UNAUTHORIZED, "잘못된 토큰입니다."),
    WRONG_REFRESH_TOKEN(UNAUTHORIZED, "잘못된 리프레시 토큰입니다."),
    INVALID_REFRESH_TOKEN(UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다."),

    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
