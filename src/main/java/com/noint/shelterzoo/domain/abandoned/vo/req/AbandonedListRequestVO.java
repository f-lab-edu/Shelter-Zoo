package com.noint.shelterzoo.domain.abandoned.vo.req;

import com.noint.shelterzoo.domain.abandoned.dto.req.AbandonedListRequestDTO;
import com.noint.shelterzoo.domain.abandoned.enums.GenderEnum;
import com.noint.shelterzoo.domain.abandoned.enums.NeuterEnum;
import lombok.Data;

@Data
public class AbandonedListRequestVO {
    private long userSeq;
    private String location;
    private String kind;
    private GenderEnum gender;
    private NeuterEnum neuter;
    private int pageNum;
    private int pageSize;

    public static AbandonedListRequestVO create(long userSeq, AbandonedListRequestDTO dto) {
        AbandonedListRequestVO vo = new AbandonedListRequestVO();
        vo.setUserSeq(userSeq);
        vo.setGender(GenderEnum.findEnumByFullText(dto.getGender()));
        vo.setNeuter(NeuterEnum.findEnumByFullText(dto.getNeuter()));
        vo.setKind(dto.getKind());
        vo.setLocation(dto.getLocation());
        vo.setPageSize(dto.getPageSize());
        vo.setPageNum(dto.getPageNum());

        return vo;
    }
}