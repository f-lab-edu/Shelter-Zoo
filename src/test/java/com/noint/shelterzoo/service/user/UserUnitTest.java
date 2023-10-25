package com.noint.shelterzoo.service.user;

import com.noint.shelterzoo.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UserService.class})
public class UserUnitTest {
    @Autowired
    UserService userService;
    @MockBean
    UserRepository userRepository;

    @Test
    @DisplayName("이메일 중복검사 : 중복")
    void isExistEmailForTrue(){
        // given
        String email = "test@email.com";
        when(userRepository.isExistEmail(email)).thenReturn(1);

        // when
        Boolean isExistEmail = userService.isExistEmail(email);

        // then
        assertEquals(isExistEmail, true);
    }

    @Test
    @DisplayName("이메일 중복검사 : 중복X")
    void isExistEmailForFalse(){
        // given
        String email = "test@email.com";
        when(userRepository.isExistEmail(email)).thenReturn(0);

        // when
        Boolean isExistEmail = userService.isExistEmail(email);

        // then
        assertEquals(isExistEmail, false);
    }
    @Test
    @DisplayName("이메일 중복검사 : 유효성 검사 실패")
    void isExistEmailForFail(){
        // given
        String email1 = "test@@email.com";
        String email2 = "test@emailcom";
        String email3 = "testemail.com";
        String email4 = "testemailcom";
        String email5 = "test1234567890423456789123456789@email.com";

        // then
        assertAll(
                () -> assertThrows(RuntimeException.class, ()-> userService.isExistEmail(email1)),
                () -> assertThrows(RuntimeException.class, ()-> userService.isExistEmail(email2)),
                () -> assertThrows(RuntimeException.class, ()-> userService.isExistEmail(email3)),
                () -> assertThrows(RuntimeException.class, ()-> userService.isExistEmail(email4)),
                () -> assertThrows(RuntimeException.class, ()-> userService.isExistEmail(email5))
        );
    }
    @Test
    @DisplayName("이메일 유효성 검사 통과")
    void emailValidCheckForTrue() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        // given
        String comEmail = "test@email.com";
        String netEmail = "test@email.net";
        final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // when
        boolean comResult = this.invokeRegexMatcher(EMAIL_REGEX, comEmail);
        boolean netResult = this.invokeRegexMatcher(EMAIL_REGEX, netEmail);

        // then
        assertAll(
                () -> assertTrue(comResult),
                () -> assertTrue(netResult)
        );
    }

    @Test
    @DisplayName("이메일 유효성 검사 실패")
    void emailValidCheckForFalse() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        // given
        String email1 = "test@@email.com";
        String email2 = "test@emailcom";
        String email3 = "testemail.com";
        String email4 = "testemailcom";
        String email5 = "test@email.comcomcn";
        final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // when
        boolean result1 = this.invokeRegexMatcher(EMAIL_REGEX, email1);
        boolean result2 = this.invokeRegexMatcher(EMAIL_REGEX, email2);
        boolean result3 = this.invokeRegexMatcher(EMAIL_REGEX, email3);
        boolean result4 = this.invokeRegexMatcher(EMAIL_REGEX, email4);
        boolean result5 = this.invokeRegexMatcher(EMAIL_REGEX, email5);
        // then
        assertAll(
                () -> assertFalse(result1),
                () -> assertFalse(result2),
                () -> assertFalse(result3),
                () -> assertFalse(result4),
                () -> assertFalse(result5)
        );
    }
    @Test
    @DisplayName("닉네임 유효성 검사 통과")
    void nicknameValidCheckForTrue() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        // given
        String enNickname = "testNick";
        String koNickname = "테스트닉네임";
        String numNickname = "432423";
        String mixNickname = "테스트Nick12";
        final String NICKNAME_REG = "^[가-힣a-zA-Z0-9]{2,10}$";

        // when
        boolean enResult = this.invokeRegexMatcher(NICKNAME_REG, enNickname);
        boolean koResult = this.invokeRegexMatcher(NICKNAME_REG, koNickname);
        boolean numResult = this.invokeRegexMatcher(NICKNAME_REG, numNickname);
        boolean mixResult = this.invokeRegexMatcher(NICKNAME_REG, mixNickname);

        // then
        assertAll(
                () -> assertTrue(enResult),
                () -> assertTrue(koResult),
                () -> assertTrue(numResult),
                () -> assertTrue(mixResult)
        );
    }

    @Test
    @DisplayName("닉네임 유효성 검사 실패")
    void nicknameValidCheckForFalse() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        // given
        String overNickname = "testNickname";
        String oneNickname = "t";
        String initialNickname = "ㅊㅊㅊ";

        final String NICKNAME_REG = "^[가-힣a-zA-Z0-9]{2,10}$";

        // when
        boolean overResult = this.invokeRegexMatcher(NICKNAME_REG, overNickname);
        boolean oneResult = this.invokeRegexMatcher(NICKNAME_REG, oneNickname);
        boolean initialResult = this.invokeRegexMatcher(NICKNAME_REG, initialNickname);

        // then
        assertAll(
                () -> assertFalse(overResult),
                () -> assertFalse(oneResult),
                () -> assertFalse(initialResult)
        );
    }

    @Test
    @DisplayName("닉네임 중복검사 : 중복")
    void isExistNicknameForTure(){
        // given
        String nickname = "test";
        when(userRepository.isExistNickname(nickname)).thenReturn(1);

        // when
        Boolean isExistNickname = userService.isExistNickname(nickname);

        // then
        assertEquals(isExistNickname, true);
    }

    @Test
    @DisplayName("닉네임 중복검사 : 중복X")
    void isExistNicknameForFalse(){
        // given
        String nickname = "test";
        when(userRepository.isExistNickname(nickname)).thenReturn(0);

        // when
        Boolean isExistNickname = userService.isExistNickname(nickname);

        // then
        assertEquals(isExistNickname, false);
    }

    @Test
    @DisplayName("닉네임 중복검사 : 유효성 검사 실패")
    void isExistNicknameForFail(){
        // given
        String overNickname = "testNickname";
        String oneNickname = "t";
        String initialNickname = "ㅊㅊㅊ";

        // then
        assertAll(
                () -> assertThrows(RuntimeException.class, ()-> userService.isExistNickname(overNickname)),
                () -> assertThrows(RuntimeException.class, ()-> userService.isExistNickname(oneNickname)),
                () -> assertThrows(RuntimeException.class, ()-> userService.isExistNickname(initialNickname))
        );
    }

    private boolean invokeRegexMatcher(String regex, String target) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<? extends UserService> clazz = userService.getClass();
        Method method = clazz.getDeclaredMethod("regexMatcher", String.class, String.class);
        method.setAccessible(true);
        boolean invoke = (boolean) method.invoke(userService, regex, target);
        method.setAccessible(false);
        return invoke;
    }
}
