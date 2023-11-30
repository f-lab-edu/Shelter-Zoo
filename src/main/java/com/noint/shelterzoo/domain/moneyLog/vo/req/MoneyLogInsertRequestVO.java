package com.noint.shelterzoo.domain.moneyLog.vo.req;

import com.noint.shelterzoo.domain.moneyLog.enums.MoneyTypeEnum;
import com.noint.shelterzoo.domain.user.enums.MoneyUpdatePurposeEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MoneyLogInsertRequestVO {
    private long userSeq;
    private MoneyTypeEnum moneyTypeEnum;
    private BigDecimal money;
    private BigDecimal totalMoney;
    private MoneyUpdatePurposeEnum purposeEnum;
    private long adoptSeq;
    private long supportLogSeq;
    private long chargeLogSeq;

    public static MoneyLogInsertRequestVO createForAdoptReservation(long userSeq, MoneyTypeEnum moneyTypeEnum, BigDecimal money, BigDecimal totalMoney, MoneyUpdatePurposeEnum purposeEnum, long adoptSeq) {
        MoneyLogInsertRequestVO vo = new MoneyLogInsertRequestVO();
        vo.setUserSeq(userSeq);
        vo.setMoneyTypeEnum(moneyTypeEnum);
        vo.setMoney(money);
        vo.setTotalMoney(totalMoney);
        vo.setPurposeEnum(purposeEnum);
        vo.setAdoptSeq(adoptSeq);

        return vo;
    }
}
