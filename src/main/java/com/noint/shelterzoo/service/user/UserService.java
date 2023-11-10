package com.noint.shelterzoo.service.user;

import com.noint.shelterzoo.dto.user.UserDTO;
import com.noint.shelterzoo.enums.UserStateEnum;
import com.noint.shelterzoo.repository.user.UserRepository;
import com.noint.shelterzoo.vo.user.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final static String NICKNAME_REG = "^[가-힣a-zA-Z0-9]{2,10}$";
    private final static String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private final static String PASSWORD_REG = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    private final static int EMAIL_MAX_LENGTH = 30;
    public void signup(UserDTO.Signup request) {
        String password = request.getPassword();
        if (!regexMatcher(PASSWORD_REG, password)){
            throw new RuntimeException("패스워드 형식 불일치. 영문 + 숫자 + 최소 8글자 조합 필수");
        }
        request.setPassword(passwordEncoder.encode(password));
        try {
            userRepository.signup(UserVO.Signup.create(request));
        } catch (DataIntegrityViolationException e) {
            log.warn("유저 회원가입 실패");
            this.signupDuplicationExceptionHandling(e, request);
        }
    }

    private void signupDuplicationExceptionHandling(Exception e, UserDTO.Signup request){
        String message = e.getMessage();
        if (Objects.requireNonNull(message).contains("nickname")){
            log.warn("닉네임 중복 : {}", request.getNickname());
            throw new RuntimeException("닉네임 중복");
        } else if (message.contains("email")) {
            log.warn("이메일 중복 : {}" , request.getEmail());
            throw new RuntimeException("이메일 중복");
        } else {
            log.error("알 수 없는 에러, {} - {}", message, request);
        }
    }

    public Boolean isExistEmail(String email){
        if (!regexMatcher(EMAIL_REGEX, email) || email.length() > EMAIL_MAX_LENGTH){
            throw new RuntimeException("이메일 형식 혹은 길이가 맞지 않습니다.");
        }
        return userRepository.isExistEmail(email) >= 1;
    }

    public Boolean isExistNickname(String nickname){
        if (!regexMatcher(NICKNAME_REG, nickname)){
            throw new RuntimeException("닉네임 형식 혹은 길이가 맞지 않습니다.");
        }
        return userRepository.isExistNickname(nickname) >= 1;
    }

    private boolean regexMatcher(String regex, String target){
        return target.matches(regex);
    }

    public UserDTO.MyInfo login(UserDTO.Login request){
        String hashedPassword = userRepository.getPasswordByEmail(request.getEmail());
        if (!StringUtils.hasLength(hashedPassword)) {
            log.warn("로그인 실패 : '{}' 해당 이메일을 가진 유저가 존재 하지 않음", request.getEmail());
            throw new RuntimeException("로그인 실패 : 이메일 또는 패스워드를 확인해 주세요");
        }

        boolean matches = passwordEncoder.matches(request.getPassword(), hashedPassword);
        if (!matches){
            log.warn("로그인 실패 : 비밀번호 불일치");
            throw new RuntimeException("로그인 실패 : 이메일 또는 패스워드를 확인해 주세요");
        }

        UserVO.MyInfo myInfo = userRepository.myInfo(request.getEmail());
        if (!UserStateEnum.isStable(myInfo.getState())) {
            log.warn("로그인 실패 : 유저 가입상태 - {}", myInfo.getState());
            throw new RuntimeException("로그인 실패 : 이메일 또는 패스워드를 확인해 주세요");
        }

        return UserDTO.MyInfo.create(myInfo);
    }
}
