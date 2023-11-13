package com.noint.shelterzoo.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum UserStateEnum {
    STABLE("가입"),
    RESIGN("탈퇴"),
    ;
    private final String state;

    public static UserStateEnum find(String state) {
        return Arrays.stream(UserStateEnum.values())
                .filter(e -> state.equals(e.getState()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("유저 가입상태를 찾을 수 없습니다."));
    }

    public static boolean isStable(String state) {
        return STABLE == find(state);
    }
}
