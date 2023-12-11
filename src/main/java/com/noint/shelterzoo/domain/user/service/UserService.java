package com.noint.shelterzoo.domain.user.service;

import com.noint.shelterzoo.domain.user.dto.req.LoginRequestDTO;
import com.noint.shelterzoo.domain.user.dto.req.SignupRequestDTO;
import com.noint.shelterzoo.domain.user.dto.res.MyInfoResponseDTO;
import com.noint.shelterzoo.domain.user.enums.UserExceptionBody;
import com.noint.shelterzoo.domain.user.enums.UserState;
import com.noint.shelterzoo.domain.user.exception.UserException;
import com.noint.shelterzoo.domain.user.repository.UserRepository;
import com.noint.shelterzoo.domain.user.vo.req.MoneyUpdateRequestVO;
import com.noint.shelterzoo.domain.user.vo.req.ResignRequestVO;
import com.noint.shelterzoo.domain.user.vo.req.SignupRequestVO;
import com.noint.shelterzoo.domain.user.vo.res.MyInfoResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

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

    @Transactional
    public void addUser(SignupRequestDTO request) {
        String password = request.getPassword();
        if (!regexMatcher(PASSWORD_REG, password)) {
            throw new UserException(UserExceptionBody.PASSWORD_INVALID);
        }
        request.setPassword(passwordEncoder.encode(password));
        try {
            userRepository.addUser(SignupRequestVO.create(request));
        } catch (DataIntegrityViolationException e) {
            signupDuplicationExceptionHandling(e, request);
        }
    }

    private void signupDuplicationExceptionHandling(Exception e, SignupRequestDTO request) {
        String errorMsg = e.getMessage();
        log.warn("유저 회원가입 에러.");
        if (!StringUtils.hasLength(errorMsg)) {
            log.warn("알 수 없는 메세지", e);
            throw new RuntimeException(e);
        }
        if (errorMsg.contains("nickname")) {
            log.warn("닉네임 중복 : " + request.getNickname());
            throw new UserException(UserExceptionBody.NICKNAME_DUPLICATE);
        } else if (errorMsg.contains("email")) {
            log.warn("이메일 중복 : " + request.getEmail());
            throw new UserException(UserExceptionBody.EMAIL_DUPLICATE);
        }
    }

    public Boolean isExistEmail(String email) {
        if (!regexMatcher(EMAIL_REGEX, email) || email.length() > EMAIL_MAX_LENGTH) {
            throw new UserException(UserExceptionBody.EMAIL_INVALID);
        }
        return userRepository.isExistEmail(email) >= 1;
    }

    public Boolean isExistNickname(String nickname) {
        if (!regexMatcher(NICKNAME_REG, nickname)) {
            throw new UserException(UserExceptionBody.NICKNAME_INVALID);
        }
        return userRepository.isExistNickname(nickname) >= 1;
    }

    private boolean regexMatcher(String regex, String target) {
        return target.matches(regex);
    }

    @Transactional(readOnly = true)
    public MyInfoResponseDTO login(LoginRequestDTO request) {
        String hashedPassword = userRepository.getPasswordByEmail(request.getEmail());
        if (!StringUtils.hasLength(hashedPassword)) {
            log.warn("로그인 실패 : '{}' 해당 이메일을 가진 유저가 존재 하지 않음", request.getEmail());
            throw new UserException(UserExceptionBody.LOGIN_FAILED);
        }

        boolean matches = passwordEncoder.matches(request.getPassword(), hashedPassword);
        if (!matches) {
            log.warn("로그인 실패 : 비밀번호 불일치");
            throw new UserException(UserExceptionBody.LOGIN_FAILED);
        }

        MyInfoResponseVO myInfo = userRepository.getUserInfo(request.getEmail());
        if (!UserState.isStable(myInfo.getState())) {
            log.warn("로그인 실패 : 유저 가입상태 - {}", myInfo.getState());
            throw new UserException(UserExceptionBody.LOGIN_FAILED);
        }

        return MyInfoResponseDTO.create(myInfo);
    }

    @Transactional(readOnly = true)
    public MyInfoResponseDTO getUserInfo(String email) {
        return MyInfoResponseDTO.create(userRepository.getUserInfo(email));
    }

    @Transactional
    public void resign(Long userSeq) {
        userRepository.updateUserState(ResignRequestVO.create(userSeq));
    }

    public BigDecimal getUserMoneyFor(Long userSeq) {
        return userRepository.getUserMoney(userSeq);
    }

    public BigDecimal getUserMoneyForUpdate(Long userSeq) {
        return userRepository.getUserMoneyForUpdate(userSeq);
    }

    public void updateUserMoney(Long userSeq, BigDecimal totalMoney) {
        userRepository.updateUserMoney(MoneyUpdateRequestVO.create(userSeq, totalMoney));
    }
}
