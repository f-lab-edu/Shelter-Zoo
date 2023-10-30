package com.noint.shelterzoo.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserStateEnumTest {
    @Test
    @DisplayName("find 테스트")
    void findTest(){
        String state = "탈퇴";
        UserStateEnum userStateEnum = UserStateEnum.find(state);
        assertEquals(userStateEnum, UserStateEnum.RESIGN);
    }

    @Test
    @DisplayName("find 테스트 : 실패")
    void findTestFail(){
        String state = "차단";
        assertThrows(IllegalArgumentException.class, () -> UserStateEnum.find(state));
    }

    @Test
    @DisplayName("유저가 가입 상태 인지 테스트")
    void isStable(){
        String state = "가입";
        boolean isStable = UserStateEnum.isStable(state);
        assertTrue(isStable);
    }

    @Test
    @DisplayName("유저가 가입 상태 인지 테스트 : 가입 상태가 아님")
    void isStableFalse(){
        String state = "탈퇴";
        boolean isStable = UserStateEnum.isStable(state);
        assertFalse(isStable);
    }
}
