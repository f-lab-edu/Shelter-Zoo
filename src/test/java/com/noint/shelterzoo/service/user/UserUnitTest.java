package com.noint.shelterzoo.service.user;

import com.noint.shelterzoo.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @DisplayName("이메일 중복검사 : form이 안맞아서 실패")
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
    @DisplayName("이메일 폼 검사 통과")
    void emailFormCheckForTrue() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        // given
        String comEmail = "test@email.com";
        String netEmail = "test@email.net";

        // when
        boolean comResult = this.invokeEmailFormCheck(comEmail);
        boolean netResult = this.invokeEmailFormCheck(netEmail);

        // then
        assertAll(
                () -> assertTrue(comResult),
                () -> assertTrue(netResult)
        );
    }

    @Test
    @DisplayName("이메일 폼 검사 실패")
    void emailFormCheckForFalse() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        // given
        String email1 = "test@@email.com";
        String email2 = "test@emailcom";
        String email3 = "testemail.com";
        String email4 = "testemailcom";
        String email5 = "test1234567890423456789123456789@email.com";

        // when
        boolean result1 = this.invokeEmailFormCheck(email1);
        boolean result2 = this.invokeEmailFormCheck(email2);
        boolean result3 = this.invokeEmailFormCheck(email3);
        boolean result4 = this.invokeEmailFormCheck(email4);
        boolean result5 = this.invokeEmailFormCheck(email5);
        // then
        assertAll(
                () -> assertFalse(result1),
                () -> assertFalse(result2),
                () -> assertFalse(result3),
                () -> assertFalse(result4),
                () -> assertFalse(result5)
        );
    }

    private boolean invokeEmailFormCheck(String email) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<? extends UserService> clazz = userService.getClass();
        Method method = clazz.getDeclaredMethod("checkEmailForm", String.class);
        method.setAccessible(true);
        boolean invoke = (boolean) method.invoke(userService, email);
        method.setAccessible(false);
        return invoke;
    }
}
