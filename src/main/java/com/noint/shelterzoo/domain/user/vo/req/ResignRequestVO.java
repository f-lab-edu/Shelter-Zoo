package com.noint.shelterzoo.domain.user.vo.req;

import com.noint.shelterzoo.domain.user.enums.UserStateEnum;
import lombok.Data;

@Data
public class ResignRequestVO {
    private Long userSeq;
    private final UserStateEnum stateEnum = UserStateEnum.RESIGN;

    public static ResignRequestVO create(Long userSeq) {
        ResignRequestVO vo = new ResignRequestVO();
        vo.setUserSeq(userSeq);
        return vo;
    }
}
