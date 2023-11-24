package com.noint.shelterzoo.domain.abandoned.vo.req;

import com.noint.shelterzoo.domain.abandoned.dto.req.AdoptReservationRequestDTO;
import com.noint.shelterzoo.domain.abandoned.enums.AdoptProcessEnum;
import lombok.Data;

@Data
public class AdoptReservationRequestVO {
    private Long userSeq;
    private Long petSeq;
    private AdoptProcessEnum adoptProcess = AdoptProcessEnum.RESERVATION;
    private String visitDate;
    // 예약 테이블 seq
    private Long seq;

    public static AdoptReservationRequestVO create(Long userSeq, AdoptReservationRequestDTO dto) {
        AdoptReservationRequestVO vo = new AdoptReservationRequestVO();
        vo.setUserSeq(userSeq);
        vo.setPetSeq(dto.getPetSeq());
        vo.setVisitDate(dto.getVisitDate());

        return vo;
    }
}
