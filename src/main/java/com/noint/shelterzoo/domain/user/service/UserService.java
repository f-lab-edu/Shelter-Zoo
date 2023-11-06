package com.noint.shelterzoo.domain.user.service;

import com.noint.shelterzoo.domain.user.dto.req.LoginRequestDTO;
import com.noint.shelterzoo.domain.user.dto.req.SignupRequestDTO;
import com.noint.shelterzoo.domain.user.dto.res.MyInfoResponseDTO;
import com.noint.shelterzoo.domain.user.exception.UserException;
import com.noint.shelterzoo.domain.user.enums.UserExceptionEnum;
import com.noint.shelterzoo.domain.user.repository.UserRepository;
import com.noint.shelterzoo.domain.user.enums.UserStateEnum;
import com.noint.shelterzoo.domain.user.vo.req.SignupRequestVO;
import com.noint.shelterzoo.domain.user.vo.res.MyInfoResponseVO;
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
    public void signup(SignupRequestDTO request) {
        String password = request.getPassword();
        if (!regexMatcher(PASSWORD_REG, password)){
            throw new UserException(UserExceptionEnum.PASSWORD_INVALID);
        }

        request.setPassword(passwordEncoder.encode(password));

        try {
            userRepository.signup(SignupRequestVO.create(request));
        } catch (DataIntegrityViolationException e) {
            String errorMsg = e.getMessage();
            log.warn("유저 회원가입 에러. param : " + errorMsg);
            if (Objects.requireNonNull(errorMsg).contains("nickname")){
                log.warn("닉네임 중복 : " + request.getNickname());
                throw new UserException(UserExceptionEnum.NICKNAME_DUPLICATE);
            } else if (errorMsg.contains("email")) {
                log.warn("이메일 중복 : " + request.getEmail());
                throw new UserException(UserExceptionEnum.EMAIL_DUPLICATE);
            }
        }
    }

    public Boolean isExistEmail(String email){
        int maxLength = 30;
        if (!regexMatcher(EMAIL_REGEX, email) || email.length() > maxLength){
            throw new UserException(UserExceptionEnum.EMAIL_INVALID);
        }
        return userRepository.isExistEmail(email) >= 1;
    }

    public Boolean isExistNickname(String nickname){
        if (!regexMatcher(NICKNAME_REG, nickname)){
            throw new UserException(UserExceptionEnum.NICKNAME_INVALID);
        }
        return userRepository.isExistNickname(nickname) >= 1;
    }

    private boolean regexMatcher(String regex, String target){
        return target.matches(regex);
    }

    public MyInfoResponseDTO login(LoginRequestDTO request){
        String hashedPassword = userRepository.getPasswordByEmail(request.getEmail());
        if (!StringUtils.hasLength(hashedPassword)) {
            log.warn("로그인 실패 : '{}' 해당 이메일을 가진 유저가 존재 하지 않음", request.getEmail());
            throw new UserException(UserExceptionEnum.LOGIN_FAILD);
        }

        boolean matches = passwordEncoder.matches(request.getPassword(), hashedPassword);
        if (!matches){
            log.warn("로그인 실패 : 비밀번호 불일치");
            throw new UserException(UserExceptionEnum.LOGIN_FAILD);
        }

        MyInfoResponseVO myInfo = userRepository.myInfo(request.getEmail());
        if (!UserStateEnum.isStable(myInfo.getState())) {
            log.warn("로그인 실패 : 유저 가입상태 - {}", myInfo.getState());
            throw new UserException(UserExceptionEnum.LOGIN_FAILD);
        }

        return MyInfoResponseDTO.create(myInfo);
    }
}
