package com.noint.shelterzoo.service.user;

import com.noint.shelterzoo.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

}
