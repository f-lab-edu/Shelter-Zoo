package com.noint.shelterzoo.domain.support.vo.req;

import com.noint.shelterzoo.domain.support.dto.req.DonateRequestDTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddSupportLogRequestVO {
    private Long userSeq;
    private Long supportPetSeq;
    private BigDecimal money;
    // supportLogSeq
    private Long seq;

    public static AddSupportLogRequestVO create(Long userSeq, DonateRequestDTO request) {
        AddSupportLogRequestVO vo = new AddSupportLogRequestVO();
        vo.setUserSeq(userSeq);
        vo.setSupportPetSeq(request.getSupportPetSeq());
        vo.setMoney(request.getDonateAmount());

        return vo;
    }
}
