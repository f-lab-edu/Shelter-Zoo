package com.noint.shelterzoo.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum UserState {
    STABLE("가입"),
    RESIGN("탈퇴"),
    ;
    private final String state;

    public static UserState find(String state) {
        return Arrays.stream(UserState.values())
                .filter(e -> state.equals(e.getState()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("유저 가입상태를 찾을 수 없습니다."));
    }

    public static boolean isStable(String state) {
        return STABLE == find(state);
    }
}
