package com.noint.shelterzoo.domain.moneyLog.dto.res;

import com.noint.shelterzoo.domain.moneyLog.vo.res.MoneyLogDetailWithChargeResVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MoneyLogDetailWithChargeResponseDTO extends MoneyLogDetailDTO {
    private String chargeId;

    public static MoneyLogDetailWithChargeResponseDTO create(MoneyLogDetailWithChargeResVO vo) {
        MoneyLogDetailWithChargeResponseDTO dto = new MoneyLogDetailWithChargeResponseDTO();
        dto.setMoneyLogSeq(vo.getMoneyLogSeq());
        dto.setUserSeq(vo.getUserSeq());
        dto.setMoneyType(vo.getMoneyType());
        dto.setMoney(vo.getMoney());
        dto.setTotalMoney(vo.getTotalMoney());
        dto.setType(vo.getType());
        dto.setCreatedAt(vo.getCreatedAt());
        dto.setChargeId(vo.getChargeId());

        return dto;
    }
}
