package com.noint.shelterzoo.domain.abandoned.vo.req;

import com.noint.shelterzoo.domain.abandoned.dto.req.AdoptReservationRequestDTO;
import com.noint.shelterzoo.domain.abandoned.enums.AdoptProcessEnum;
import lombok.Data;

@Data
public class AdoptProcessUpdateRequestVO {
    private long petSeq;
    private AdoptProcessEnum adoptProcess = AdoptProcessEnum.RESERVATION;

    public static AdoptProcessUpdateRequestVO create(AdoptReservationRequestDTO dto) {
        AdoptProcessUpdateRequestVO vo = new AdoptProcessUpdateRequestVO();
        vo.setPetSeq(dto.getPetSeq());

        return vo;
    }
}
