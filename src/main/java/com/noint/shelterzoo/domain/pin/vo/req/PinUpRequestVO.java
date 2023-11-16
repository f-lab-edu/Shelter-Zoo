package com.noint.shelterzoo.domain.pin.vo.req;

import lombok.Data;

@Data
public class PinUpRequestVO {
    private Long userSeq;
    private Long petSeq;

    public static PinUpRequestVO create(Long userSeq, Long petSeq) {
        PinUpRequestVO vo = new PinUpRequestVO();
        vo.setPetSeq(petSeq);
        vo.setUserSeq(userSeq);

        return vo;
    }
}
