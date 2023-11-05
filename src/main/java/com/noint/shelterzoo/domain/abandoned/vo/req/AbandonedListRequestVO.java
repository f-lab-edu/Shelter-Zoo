package com.noint.shelterzoo.domain.abandoned.vo.req;

import com.noint.shelterzoo.domain.abandoned.dto.req.AbandonedListRequestDTO;
import com.noint.shelterzoo.domain.abandoned.enums.GenderEnum;
import com.noint.shelterzoo.domain.abandoned.enums.NeuterEnum;
import com.noint.shelterzoo.general.ListPageGeneral;
import lombok.Data;

@Data
public class AbandonedListRequestVO extends ListPageGeneral {
    private long userSeq;
    private String location;
    private String kind;
    private GenderEnum gender;
    private NeuterEnum neuter;
    private long lastContentSeq;
    private int page;

    public static AbandonedListRequestVO create(long userSeq, AbandonedListRequestDTO dto){
        AbandonedListRequestVO vo = new AbandonedListRequestVO();
        vo.setUserSeq(userSeq);
        vo.setGender(GenderEnum.findEnumByFullText(dto.getGender()));
        vo.setNeuter(NeuterEnum.findEnumByFullText(dto.getNeuter()));
        vo.setKind(dto.getKind());
        vo.setLocation(dto.getLocation());
        vo.setLastContentSeq(dto.getLastContentSeq());
        vo.setPage(pageLength + 1);
        
        return vo;
    }
}