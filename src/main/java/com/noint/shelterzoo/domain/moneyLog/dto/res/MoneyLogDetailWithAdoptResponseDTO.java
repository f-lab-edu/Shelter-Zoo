package com.noint.shelterzoo.domain.moneyLog.dto.res;

import com.noint.shelterzoo.domain.moneyLog.vo.res.MoneyLogDetailWithAdoptResVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class MoneyLogDetailWithAdoptResponseDTO extends MoneyDetailDTO {
    private Long moneyLogSeq;
    private Long userSeq;
    private String moneyType;
    private BigDecimal money;
    private BigDecimal totalMoney;
    private String type;
    private String createdAt;
    private String adoptState;
    private Long petSeq;
    private String petThumbnail;

    public static MoneyLogDetailWithAdoptResponseDTO create(MoneyLogDetailWithAdoptResVO vo) {
        MoneyLogDetailWithAdoptResponseDTO dto = new MoneyLogDetailWithAdoptResponseDTO();
        dto.setMoneyLogSeq(vo.getMoneyLogSeq());
        dto.setUserSeq(vo.getUserSeq());
        dto.setMoneyType(vo.getMoneyType());
        dto.setMoney(vo.getMoney());
        dto.setTotalMoney(vo.getTotalMoney());
        dto.setType(vo.getType());
        dto.setCreatedAt(vo.getCreatedAt());
        dto.setAdoptState(vo.getAdoptState());
        dto.setPetSeq(vo.getPetSeq());
        dto.setPetThumbnail(vo.getPetThumbnail());

        return dto;
    }
}
