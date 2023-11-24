package com.noint.shelterzoo.domain.user.service;

import com.noint.shelterzoo.domain.moneyLog.enums.MoneyTypeEnum;
import com.noint.shelterzoo.domain.moneyLog.service.MoneyLogService;
import com.noint.shelterzoo.domain.user.dto.req.LoginRequestDTO;
import com.noint.shelterzoo.domain.user.dto.req.SignupRequestDTO;
import com.noint.shelterzoo.domain.user.dto.res.MyInfoResponseDTO;
import com.noint.shelterzoo.domain.user.enums.MoneyUpdatePurposeEnum;
import com.noint.shelterzoo.domain.user.enums.UserExceptionEnum;
import com.noint.shelterzoo.domain.user.enums.UserStateEnum;
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
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MoneyLogService moneyLogService;

    private final static String NICKNAME_REG = "^[가-힣a-zA-Z0-9]{2,10}$";
    private final static String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private final static String PASSWORD_REG = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    private final static int EMAIL_MAX_LENGTH = 30;

    public void signup(SignupRequestDTO request) {
        String password = request.getPassword();
        if (!regexMatcher(PASSWORD_REG, password)) {
            throw new UserException(UserExceptionEnum.PASSWORD_INVALID);
        }
        request.setPassword(passwordEncoder.encode(password));
        try {
            userRepository.signup(SignupRequestVO.create(request));
        } catch (DataIntegrityViolationException e) {
            log.warn("유저 회원가입 실패");
            signupDuplicationExceptionHandling(e, request);
        }
    }

    private void signupDuplicationExceptionHandling(Exception e, SignupRequestDTO request) {
        String errorMsg = e.getMessage();
        log.warn("유저 회원가입 에러. param : " + errorMsg);
        if (Objects.requireNonNull(errorMsg).contains("nickname")) {
            log.warn("닉네임 중복 : " + request.getNickname());
            throw new UserException(UserExceptionEnum.NICKNAME_DUPLICATE);
        } else if (errorMsg.contains("email")) {
            log.warn("이메일 중복 : " + request.getEmail());
            throw new UserException(UserExceptionEnum.EMAIL_DUPLICATE);
        }
    }

    public Boolean isExistEmail(String email) {
        if (!regexMatcher(EMAIL_REGEX, email) || email.length() > EMAIL_MAX_LENGTH) {
            throw new UserException(UserExceptionEnum.EMAIL_INVALID);
        }
        return userRepository.isExistEmail(email) >= 1;
    }

    public Boolean isExistNickname(String nickname) {
        if (!regexMatcher(NICKNAME_REG, nickname)) {
            throw new UserException(UserExceptionEnum.NICKNAME_INVALID);
        }
        return userRepository.isExistNickname(nickname) >= 1;
    }

    private boolean regexMatcher(String regex, String target) {
        return target.matches(regex);
    }

    public MyInfoResponseDTO login(LoginRequestDTO request) {
        String hashedPassword = userRepository.getPasswordByEmail(request.getEmail());
        if (!StringUtils.hasLength(hashedPassword)) {
            log.warn("로그인 실패 : '{}' 해당 이메일을 가진 유저가 존재 하지 않음", request.getEmail());
            throw new UserException(UserExceptionEnum.LOGIN_FAILED);
        }

        boolean matches = passwordEncoder.matches(request.getPassword(), hashedPassword);
        if (!matches) {
            log.warn("로그인 실패 : 비밀번호 불일치");
            throw new UserException(UserExceptionEnum.LOGIN_FAILED);
        }

        MyInfoResponseVO myInfo = userRepository.myInfo(request.getEmail());
        if (!UserStateEnum.isStable(myInfo.getState())) {
            log.warn("로그인 실패 : 유저 가입상태 - {}", myInfo.getState());
            throw new UserException(UserExceptionEnum.LOGIN_FAILED);
        }

        return MyInfoResponseDTO.create(myInfo);
    }

    public MyInfoResponseDTO myInfo(String email) {
        return MyInfoResponseDTO.create(userRepository.myInfo(email));
    }

    public void resign(Long userSeq) {
        userRepository.resign(ResignRequestVO.create(userSeq));
    }

    public BigDecimal getUserMoney(Long userSeq) {
        return userRepository.getUserMoneyForUpdate(userSeq);
    }

    @Transactional
    public void userMoneyUpdate(Long userSeq, BigDecimal totalMoney, BigDecimal amount, MoneyTypeEnum moneyTypeEnum, MoneyUpdatePurposeEnum purposeEnum, Long targetTableSeq) {
        userRepository.userMoneyUpdate(MoneyUpdateRequestVO.create(userSeq, totalMoney));
        moneyLogService.moneyLogInsertForAdoptReservation(userSeq, totalMoney, amount, moneyTypeEnum, purposeEnum, targetTableSeq);
    }
}
