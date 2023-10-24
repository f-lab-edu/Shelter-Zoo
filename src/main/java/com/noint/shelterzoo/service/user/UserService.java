package com.noint.shelterzoo.service.user;

import com.noint.shelterzoo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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
}
