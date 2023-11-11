package com.noint.shelterzoo.domain.abandoned.dto.req;

import lombok.Data;

@Data
public class AdoptReservationRequestDTO {
    private long userSeq;
    private long petSeq;
    private String visitDate;
}
