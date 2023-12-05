package com.noint.shelterzoo.domain.moneyLog.dto.res;

import com.noint.shelterzoo.domain.moneyLog.vo.res.MoneyLogDetailWithSupportResVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MoneyLogDetailWithSupportResponseDTO extends MoneyLogDetailDTO {
    private String disease;
    private String petThumbnail;

    public static MoneyLogDetailWithSupportResponseDTO create(MoneyLogDetailWithSupportResVO vo) {
        MoneyLogDetailWithSupportResponseDTO dto = new MoneyLogDetailWithSupportResponseDTO();
        dto.setMoneyLogSeq(vo.getMoneyLogSeq());
        dto.setUserSeq(vo.getUserSeq());
        dto.setMoneyType(vo.getMoneyType());
        dto.setMoney(vo.getMoney());
        dto.setTotalMoney(vo.getTotalMoney());
        dto.setType(vo.getType());
        dto.setCreatedAt(vo.getCreatedAt());
        dto.setDisease(vo.getDisease());
        dto.setPetThumbnail(vo.getPetThumbnail());

        return dto;
    }
}
