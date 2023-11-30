package com.noint.shelterzoo.domain.abandoned.vo.req;

import com.noint.shelterzoo.domain.abandoned.dto.req.AdoptReservationRequestDTO;
import com.noint.shelterzoo.domain.abandoned.dto.req.AdoptUpdateRequestDTO;
import com.noint.shelterzoo.domain.abandoned.enums.AdoptProcessEnum;
import lombok.Data;

@Data
public class AdoptProcessUpdateRequestVO {
    private Long petSeq;
    private AdoptProcessEnum adoptProcess;

    public static AdoptProcessUpdateRequestVO create(AdoptReservationRequestDTO dto) {
        AdoptProcessUpdateRequestVO vo = new AdoptProcessUpdateRequestVO();
        vo.setPetSeq(dto.getPetSeq());
        vo.setAdoptProcess(AdoptProcessEnum.RESERVATION);

        return vo;
    }

    public static AdoptProcessUpdateRequestVO create(AdoptUpdateRequestDTO dto) {
        AdoptProcessUpdateRequestVO vo = new AdoptProcessUpdateRequestVO();
        vo.setPetSeq(dto.getPetSeq());
        vo.setAdoptProcess(AdoptProcessEnum.findEnumByStateStr(dto.getState()));

        return vo;
    }

}
