package com.noint.shelterzoo.domain.abandoned.vo.req;

import lombok.Data;

@Data
public class PinUpRequestVO {
    private long userSeq;
    private long petSeq;

    public static PinUpRequestVO create(long userSeq, long petSeq) {
        PinUpRequestVO vo = new PinUpRequestVO();
        vo.setPetSeq(petSeq);
        vo.setUserSeq(userSeq);

        return vo;
    }
}
