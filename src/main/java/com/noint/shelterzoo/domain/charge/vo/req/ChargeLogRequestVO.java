package com.noint.shelterzoo.domain.charge.vo.req;

import com.noint.shelterzoo.domain.charge.dto.req.ChargeMoneyRequestDTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChargeLogRequestVO {
    private Long userSeq;
    private BigDecimal chargeAmount;
    private BigDecimal totalMoney;
    private String chargeId;
    // chargeLog Seq
    private Long seq;

    public static ChargeLogRequestVO create(Long userSeq, BigDecimal totalMoney, ChargeMoneyRequestDTO dto) {
        ChargeLogRequestVO vo = new ChargeLogRequestVO();
        vo.setChargeId(dto.getChargeId());
        vo.setChargeAmount(dto.getChargeAmount());
        vo.setTotalMoney(totalMoney);
        vo.setUserSeq(userSeq);

        return vo;
    }
}
