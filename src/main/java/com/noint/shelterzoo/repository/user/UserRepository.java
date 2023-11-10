package com.noint.shelterzoo.repository.user;

import com.noint.shelterzoo.vo.user.UserVO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    int isExistEmail(String email);
    int isExistNickname(String nickname);
    void signup(UserVO.Signup params);
}
