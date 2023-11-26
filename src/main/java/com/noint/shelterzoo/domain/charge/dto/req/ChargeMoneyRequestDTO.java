package com.noint.shelterzoo.domain.charge.dto.req;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChargeMoneyRequestDTO {
    private String chargeId;
    private BigDecimal chargeAmount;
}
