package com.noint.shelterzoo.domain.user.vo.req;

import com.noint.shelterzoo.domain.user.enums.UserState;
import lombok.Data;

@Data
public class ResignRequestVO {
    private Long userSeq;
    private final UserState stateEnum = UserState.RESIGN;

    public static ResignRequestVO create(Long userSeq) {
        ResignRequestVO vo = new ResignRequestVO();
        vo.setUserSeq(userSeq);
        return vo;
    }
}
