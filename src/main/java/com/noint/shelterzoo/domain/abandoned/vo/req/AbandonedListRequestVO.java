package com.noint.shelterzoo.domain.abandoned.vo.req;

import com.noint.shelterzoo.domain.abandoned.dto.req.AbandonedListRequestDTO;
import com.noint.shelterzoo.domain.abandoned.enums.PetGender;
import com.noint.shelterzoo.domain.abandoned.enums.Neuter;
import lombok.Data;

@Data
public class AbandonedListRequestVO {
    private Long userSeq;
    private String location;
    private String kind;
    private PetGender gender;
    private Neuter neuter;
    private Integer pageNum;
    private Integer pageSize;

    public static AbandonedListRequestVO create(Long userSeq, AbandonedListRequestDTO dto) {
        AbandonedListRequestVO vo = new AbandonedListRequestVO();
        vo.setUserSeq(userSeq);
        vo.setGender(PetGender.findEnumByFullText(dto.getGender()));
        vo.setNeuter(Neuter.findEnumByFullText(dto.getNeuter()));
        vo.setKind(dto.getKind());
        vo.setLocation(dto.getLocation());
        vo.setPageSize(dto.getPageSize());
        vo.setPageNum(dto.getPageNum());

        return vo;
    }
}