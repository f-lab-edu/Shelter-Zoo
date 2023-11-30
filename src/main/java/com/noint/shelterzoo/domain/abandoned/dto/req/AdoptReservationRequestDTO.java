package com.noint.shelterzoo.domain.abandoned.dto.req;

import lombok.Data;

@Data
public class AdoptReservationRequestDTO {
    private Long userSeq;
    private Long petSeq;
    private String visitDate;
}
