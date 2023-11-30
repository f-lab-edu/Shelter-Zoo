package com.noint.shelterzoo.domain.abandoned.vo.res;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ReservationCheckResponseVO {
    private long adoptSeq;
    private String state;
}
