package com.noint.shelterzoo.domain.moneyLog.vo.req;

import com.noint.shelterzoo.domain.moneyLog.enums.MoneyLogExceptionEnum;
import com.noint.shelterzoo.domain.moneyLog.enums.MoneyTypeEnum;
import com.noint.shelterzoo.domain.moneyLog.exception.MoneyLogException;
import com.noint.shelterzoo.domain.user.enums.MoneyUpdatePurposeEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MoneyLogInsertRequestVO {
    private Long userSeq;
    private MoneyTypeEnum moneyTypeEnum;
    private BigDecimal money;
    private BigDecimal totalMoney;
    private MoneyUpdatePurposeEnum purposeEnum;
    private Long adoptSeq;
    private Long supportLogSeq;
    private Long chargeLogSeq;

    public static MoneyLogInsertRequestVO create(Long userSeq, MoneyTypeEnum moneyTypeEnum, BigDecimal money, BigDecimal totalMoney, MoneyUpdatePurposeEnum purposeEnum, Long targetSeq) {
        MoneyLogInsertRequestVO vo = new MoneyLogInsertRequestVO();
        vo.setUserSeq(userSeq);
        vo.setMoneyTypeEnum(moneyTypeEnum);
        vo.setMoney(money);
        vo.setTotalMoney(totalMoney);
        vo.setPurposeEnum(purposeEnum);
        matchTargetSeq(vo, purposeEnum, targetSeq);

        return vo;
    }

    private static void matchTargetSeq(MoneyLogInsertRequestVO vo, MoneyUpdatePurposeEnum purposeEnum, Long targetSeq) {
        switch (purposeEnum) {
            case CHARGE:
                vo.setChargeLogSeq(targetSeq);
                break;
            case SUPPORT:
                vo.setSupportLogSeq(targetSeq);
                break;
            case ADOPT_PAYBACK:
            case ADOPT_RESERVATION:
                vo.setAdoptSeq(targetSeq);
                break;
            default:
                throw new MoneyLogException(MoneyLogExceptionEnum.UNKNOWN_LOG_TYPE);
        }
    }
}
