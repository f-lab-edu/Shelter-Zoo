package com.noint.shelterzoo.service.user;

import com.noint.shelterzoo.dto.user.UserDTO;
import com.noint.shelterzoo.repository.user.UserRepository;
import com.noint.shelterzoo.vo.user.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final static String NICKNAME_REG = "^[가-힣a-zA-Z0-9]{2,10}$";
    private final static String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private final static String PASSWORD_REG = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    public void signup(UserDTO.Signup request) {
        String password = request.getPassword();
        if (!regexMatcher(PASSWORD_REG, password)){
            throw new RuntimeException("패스워드 형식 불일치. 영문 + 숫자 + 최소 8글자 조합 필수");
        }

        request.setPassword(new BCryptPasswordEncoder().encode(password));

        try {
            userRepository.signup(UserVO.Signup.create(request));
        } catch (DataIntegrityViolationException e) {
            String errorMsg = e.getCause().getMessage();
            if (errorMsg.contains("nickname")){
                throw new RuntimeException("닉네임 중복");
            } else if (errorMsg.contains("email")) {
                throw new RuntimeException("이메일 중복");
            }
        }
    }

    public Boolean isExistEmail(String email){
        int maxLength = 30;
        if (!regexMatcher(EMAIL_REGEX, email) || email.length() > maxLength){
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
}
