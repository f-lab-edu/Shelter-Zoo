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

    public Boolean isExistEmail(String email){
        if (!checkEmailForm(email)){
            throw new RuntimeException("이메일 형식 혹은 길이가 맞지 않습니다.");
        }
        return userRepository.isExistEmail(email) >= 1;
    }

    private boolean checkEmailForm(String email){
        String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        int maxLength = 30;
        boolean isOverMax = email.length() > maxLength;
        boolean isRightForm = email.matches(EMAIL_REGEX);
        return !isOverMax && isRightForm;
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
