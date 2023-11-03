package com.noint.shelterzoo.domain.user.repository;

import com.noint.shelterzoo.domain.user.vo.UserVO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    int isExistEmail(String email);
    int isExistNickname(String nickname);
    void signup(UserVO.Signup params);
    String getPasswordByEmail(String email);
    UserVO.MyInfo myInfo(String email);
}
