package com.noint.shelterzoo.domain.support.dto.req;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DonateRequestDTO {
    private Long supportPetSeq;
    private BigDecimal donateAmount;
}
