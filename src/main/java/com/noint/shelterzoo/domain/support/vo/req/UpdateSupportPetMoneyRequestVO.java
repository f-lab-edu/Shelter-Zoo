package com.noint.shelterzoo.domain.support.vo.req;

import com.noint.shelterzoo.domain.support.dto.req.DonateRequestDTO;
import com.noint.shelterzoo.domain.support.vo.res.SupportMoneysResponseVO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateSupportPetMoneyRequestVO {
    private BigDecimal updateSupportedMoney;
    private Long supportPetSeq;
    private Boolean isComplete;

    public static UpdateSupportPetMoneyRequestVO create(SupportMoneysResponseVO moneysAboutSupportPet, DonateRequestDTO request) {
        UpdateSupportPetMoneyRequestVO vo = new UpdateSupportPetMoneyRequestVO();
        vo.setUpdateSupportedMoney(moneysAboutSupportPet.getSupportedMoney().add(request.getDonateAmount()));
        vo.setSupportPetSeq(request.getSupportPetSeq());
        vo.setIsComplete(vo.getUpdateSupportedMoney().equals(moneysAboutSupportPet.getRequiredMoney()));

        return vo;
    }
}
