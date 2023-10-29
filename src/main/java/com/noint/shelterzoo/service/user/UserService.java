package com.noint.shelterzoo.service.user;

import com.noint.shelterzoo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final static String NICKNAME_REG = "^[가-힣a-zA-Z0-9]{2,10}$";
    private final static String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private final static String PASSWORD_REG = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

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
