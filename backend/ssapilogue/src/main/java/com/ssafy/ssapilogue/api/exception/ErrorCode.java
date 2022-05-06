package com.ssafy.ssapilogue.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    NO_USER(BAD_REQUEST, "존재하지 않는 유저입니다. 회원가입을 진행해주세요."),
    NULL_PASSWORD(BAD_REQUEST, "비밀번호를 입력해주세요."),
    ALREADY_EXIST_USER(BAD_REQUEST, "이미 가입한 회원입니다."),
    NO_TOKEN(BAD_REQUEST, "header에 토큰이 존재하지 않습니다."),
    NO_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 존재하지 않습니다."),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_USER(UNAUTHORIZED, "ssapilogue를 이용할 수 없습니다."),
    WRONG_PASSWORD(UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    EXPIRED_TOKEN(UNAUTHORIZED, "만료된 토큰입니다."),
    WRONG_TOKEN(UNAUTHORIZED, "잘못된 토큰입니다."),
    WRONG_REFRESH_TOKEN(UNAUTHORIZED, "잘못된 리프레시 토큰입니다."),
    INVALID_REFRESH_TOKEN(UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다."),

    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    PROJECT_NOT_FOUND(NOT_FOUND, "해당하는 정보의 프로젝트를 찾을 수 없습니다."),
    SURVEY_NOT_FOUND(NOT_FOUND, "해당하는 정보의 설문조사를 찾을 수 없습니다."),
    SURVEY_OPTION_NOT_FOUND(NOT_FOUND, "해당하는 정보의 설문조사 옵션을 찾을 수 없습니다."),
    BUGREPORT_NOT_FOUND(NOT_FOUND, "해당하는 정보의 버그 리포트를 찾을 수 없습니다."),

    /* 409 : CONFLICT : Resource의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
