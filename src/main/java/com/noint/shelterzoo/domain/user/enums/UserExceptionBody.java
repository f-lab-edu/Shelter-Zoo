package com.noint.shelterzoo.domain.user.enums;

import com.noint.shelterzoo.exception.ExceptionEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserExceptionBody implements ExceptionEnum {
    EMAIL_DUPLICATE(400, "중복된 이메일"),
    EMAIL_INVALID(400, "이메일 유효성 검사 실패"),
    NICKNAME_DUPLICATE(400, "중복된 닉네임"),
    NICKNAME_INVALID(400, "닉네임 유효성 검사 실패"),
    PASSWORD_NOT_MATCH(400, "비밀번호 불일치"),
    PASSWORD_INVALID(400, "비밀번호 유효성 검사 실패"),
    LOGIN_FAILED(400, "로그인 실패"),
    LOGIN_INFO_EMPTY(404, "로그인 정보 없음"),
    ;

    private final int code;
    private final String message;
}
