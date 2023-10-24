package com.noint.shelterzoo.controller.user;

import com.noint.shelterzoo.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    @DisplayName("이메일 중복검사 테스트")
    void emailDuplicateCheck() throws Exception {
        // given
        String email = "test@email.com";
        given(userService.isExistEmail(email)).willReturn(true);

        // when
        ResultActions actions = mockMvc.perform(
                get("/user/email/check/{email}", email)
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andDo(print());

        verify(userService).isExistEmail(email);
    }
}
