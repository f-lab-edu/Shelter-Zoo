package com.noint.shelterzoo.service.user;

import com.noint.shelterzoo.config.PasswordEncoderConfig;
import com.noint.shelterzoo.domain.user.dto.req.LoginRequestDTO;
import com.noint.shelterzoo.domain.user.dto.req.SignupRequestDTO;
import com.noint.shelterzoo.domain.user.dto.res.MyInfoResponseDTO;
import com.noint.shelterzoo.domain.user.repository.UserRepository;
import com.noint.shelterzoo.domain.user.service.UserService;
import com.noint.shelterzoo.domain.user.vo.req.SignupRequestVO;
import com.noint.shelterzoo.domain.user.vo.res.MyInfoResponseVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {UserService.class, PasswordEncoderConfig.class})
public class UserServiceUnitTest {
    @Autowired
    UserService userService;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @MockBean
    UserRepository userRepository;

    @Test
    @DisplayName("회원가입 : 성공")
    void signupSuccess(){
        // given
        SignupRequestDTO testUser = new SignupRequestDTO();
        testUser.setEmail("testEmail@email.com");
        testUser.setPassword("password4");
        testUser.setNickname("testNick");

        doNothing().when(userRepository).signup(any());
        // when
        userService.signup(testUser);

        // then
        verify(userRepository, times(1)).signup(SignupRequestVO.create(testUser));
    }
    @Test
    @DisplayName("회원가입 : 이메일 중복 실패")
    void signupFailByDuplicateEmail(){
        // given
        SignupRequestDTO testUser = new SignupRequestDTO();
        testUser.setEmail("test3@email.com");
        testUser.setPassword("password1");
        testUser.setNickname("test3");
        doThrow(new DataIntegrityViolationException("Duplicate entry 'test3@email.com' for key 'user.email_UNIQUE'"))
                .when(userRepository).signup(any());
        // when&then
        assertThrows(RuntimeException.class, () -> userService.signup(testUser));
    }

    @Test
    @DisplayName("회원가입 : 닉네임 중복 실패")
    void signupFailByDuplicateNickname(){
        // given
        SignupRequestDTO testUser = new SignupRequestDTO();
        testUser.setEmail("test3@email.com");
        testUser.setPassword("password1");
        testUser.setNickname("test3");

        doThrow(new DataIntegrityViolationException("Duplicate entry 'test3' for key 'user.nickname_UNIQUE'"))
                .when(userRepository).signup(any());
        // when&then
        assertThrows(RuntimeException.class, () -> userService.signup(testUser));
    }
    @Test
    @DisplayName("회원가입 : 비밀번호 유효성 검사 실패")
    void signupFailByPasswordValid(){
        // given
        SignupRequestDTO testUser = new SignupRequestDTO();
        testUser.setEmail("testEmail@email.com");
        testUser.setPassword("password");
        testUser.setNickname("testNick");

        // when&then
        assertThrows(RuntimeException.class, () -> userService.signup(testUser));
    }
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

    @Test
    @DisplayName("비밀번호 유효성 검사 통과")
    void passwordValidCheckForTrue() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        // given
        String pass1 = "passpsdf3";
        String pass2 = "pas33333";
        String pass3 = "3passpsdf";
        String pass4 = "3333psdf";
        // 영문 + 숫자 + 최소 8글자
        final String PASSWORD_REG = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

        // when
        boolean result1 = this.invokeRegexMatcher(PASSWORD_REG, pass1);
        boolean result2 = this.invokeRegexMatcher(PASSWORD_REG, pass2);
        boolean result3 = this.invokeRegexMatcher(PASSWORD_REG, pass3);
        boolean result4 = this.invokeRegexMatcher(PASSWORD_REG, pass4);

        // then
        assertAll(
                () -> assertTrue(result1),
                () -> assertTrue(result2),
                () -> assertTrue(result3),
                () -> assertTrue(result4)
        );
    }
    @Test
    @DisplayName("비밀번호 유효성 검사 실패")
    void passwordValidCheckForFalse() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        // given
        String pass1 = "pas2";
        String pass2 = "passwervcx";
        String pass3 = "333333666";
        // 영문 + 숫자 + 최소 8글자
        final String PASSWORD_REG = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

        // when
        boolean result1 = this.invokeRegexMatcher(PASSWORD_REG, pass1);
        boolean result2 = this.invokeRegexMatcher(PASSWORD_REG, pass2);
        boolean result3 = this.invokeRegexMatcher(PASSWORD_REG, pass3);

        // then
        assertAll(
                () -> assertFalse(result1),
                () -> assertFalse(result2),
                () -> assertFalse(result3)
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

    @Test
    @DisplayName("로그인 실패 : 이메일 불일치")
    void loginFailByEmail() {
        // given
        LoginRequestDTO login = new LoginRequestDTO();
        login.setEmail("test30@email.com");
        login.setPassword("password1");

        when(userRepository.getPasswordByEmail(any())).thenReturn("");
        // when then
        assertThrows(RuntimeException.class, () -> userService.login(login));
    }
    @Test
    @DisplayName("로그인 실패 : 패스워드 불일치")
    void loginFailByPassword() {
        // given
        LoginRequestDTO login = new LoginRequestDTO();
        login.setEmail("test3@email.com");
        login.setPassword("1password1");

        when(userRepository.getPasswordByEmail(any()))
                .thenReturn("$2a$10$dRUqYhH39O9DKfmz//CAReWHAlFxKhaBdd.Lby6fYB2YE1AJ506UC");

        // when then
        assertThrows(RuntimeException.class, () -> userService.login(login));
    }

    @Test
    @DisplayName("로그인 실패 : 가입 상태가 아닌 유저")
    void loginFailByState() {
        // given
        LoginRequestDTO login = new LoginRequestDTO();
        login.setEmail("test3@email.com");
        login.setPassword("password1");

        MyInfoResponseVO hopeValue = new MyInfoResponseVO();
        hopeValue.setState("탈퇴");

        when(userRepository.getPasswordByEmail(any()))
                .thenReturn("$2a$10$dRUqYhH39O9DKfmz//CAReWHAlFxKhaBdd.Lby6fYB2YE1AJ506UC");
        when(userRepository.myInfo(any())).thenReturn(hopeValue);

        // when then
        assertThrows(RuntimeException.class, () -> userService.login(login));

        verify(userRepository, times(1)).getPasswordByEmail(any());
        verify(userRepository, times(1)).myInfo(any());
    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() {
        // given
        LoginRequestDTO login = new LoginRequestDTO();
        login.setEmail("test3@email.com");
        login.setPassword("password1");

        MyInfoResponseVO hopeValue = new MyInfoResponseVO();
        hopeValue.setSeq(3L);
        hopeValue.setEmail("test3@email.com");
        hopeValue.setMoney(BigDecimal.valueOf(0));
        hopeValue.setCreatedAt("2023-10-30 06:46:04");
        hopeValue.setNickname("test3");
        hopeValue.setState("가입");

        when(userRepository.getPasswordByEmail(any()))
                .thenReturn("$2a$10$dRUqYhH39O9DKfmz//CAReWHAlFxKhaBdd.Lby6fYB2YE1AJ506UC");
        when(userRepository.myInfo(any())).thenReturn(hopeValue);

        // when
        MyInfoResponseDTO result = userService.login(login);

        //then
        assertEquals(MyInfoResponseDTO.create(hopeValue), result);

        verify(userRepository, times(1)).getPasswordByEmail(any());
        verify(userRepository, times(1)).myInfo(any());
    }
}
