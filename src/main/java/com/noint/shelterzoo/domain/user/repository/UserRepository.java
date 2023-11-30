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

    void signup(SignupRequestVO params);

    String getPasswordByEmail(String email);

    MyInfoResponseVO myInfo(String email);

    void resign(ResignRequestVO param);

    BigDecimal getUserMoneyForUpdate(Long userSeq);

    void userMoneyUpdate(MoneyUpdateRequestVO params);
}
