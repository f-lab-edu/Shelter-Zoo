package com.noint.shelterzoo.domain.user.vo.req;

import com.noint.shelterzoo.domain.user.enums.UserStateEnum;
import lombok.Data;

@Data
public class ResignRequestVO {
    private long seq;
    private final UserStateEnum stateEnum = UserStateEnum.RESIGN;

    public static ResignRequestVO create(Long seq) {
        ResignRequestVO vo = new ResignRequestVO();
        vo.setSeq(seq);
        return vo;
    }
}
