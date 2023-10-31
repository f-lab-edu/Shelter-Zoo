package com.noint.shelterzoo.repository.user;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    int isExistEmail(String email);
    int isExistNickname(String nickname);
}
