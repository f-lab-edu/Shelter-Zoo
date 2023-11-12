package com.noint.shelterzoo.domain.abandoned.vo.req;

import com.noint.shelterzoo.domain.abandoned.dto.req.AdoptUpdateRequestDTO;
import com.noint.shelterzoo.domain.abandoned.enums.AdoptProcessEnum;
import lombok.Data;

@Data
public class AdoptUpdateRequestVO {
    private long userSeq;
    private long petSeq;
    private AdoptProcessEnum adoptProcess;

    public static AdoptUpdateRequestVO create(long userSeq, AdoptUpdateRequestDTO dto) {
        AdoptUpdateRequestVO vo = new AdoptUpdateRequestVO();
        vo.setUserSeq(userSeq);
        vo.setPetSeq(dto.getPetSeq());
        vo.setAdoptProcess(AdoptProcessEnum.findEnumByStateStr(dto.getState()));

        return vo;
    }
}
