package com.noint.shelterzoo.domain.user.repository;

import com.noint.shelterzoo.domain.user.vo.req.MoneyUpdateRequestVO;
import com.noint.shelterzoo.domain.user.vo.req.ResignRequestVO;
import com.noint.shelterzoo.domain.user.vo.req.SignupRequestVO;
import com.noint.shelterzoo.domain.user.vo.res.MyInfoResponseVO;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface UserRepository {
    int isExistEmail(String email);

    int isExistNickname(String nickname);

    void addUser(SignupRequestVO params);

    String getPasswordByEmail(String email);

    MyInfoResponseVO getUserInfo(String email);

    void updateUserState(ResignRequestVO param);

    BigDecimal getUserMoney(Long userSeq);

    BigDecimal getUserMoneyForUpdate(Long userSeq);

    void updateUserMoney(MoneyUpdateRequestVO params);
}
