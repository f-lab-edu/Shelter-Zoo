package com.noint.shelterzoo.domain.abandoned.vo.req;

import com.noint.shelterzoo.domain.abandoned.dto.req.AdoptUpdateRequestDTO;
import com.noint.shelterzoo.domain.abandoned.enums.AdoptProcess;
import lombok.Data;

@Data
public class AdoptUpdateRequestVO {
    private Long userSeq;
    private Long petSeq;
    private AdoptProcess adoptProcess;

    public static AdoptUpdateRequestVO create(Long userSeq, AdoptUpdateRequestDTO dto) {
        AdoptUpdateRequestVO vo = new AdoptUpdateRequestVO();
        vo.setUserSeq(userSeq);
        vo.setPetSeq(dto.getPetSeq());
        vo.setAdoptProcess(AdoptProcess.findEnumByStateStr(dto.getState()));

        return vo;
    }
}
