package com.noint.shelterzoo.service.user;

import com.noint.shelterzoo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Boolean isExistEmail(String email){
        return userRepository.isExistEmail(email) >= 1;
    }
}
