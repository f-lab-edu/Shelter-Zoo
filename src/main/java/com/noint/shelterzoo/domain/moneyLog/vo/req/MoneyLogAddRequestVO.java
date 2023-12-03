package com.noint.shelterzoo.domain.moneyLog.vo.req;

import com.noint.shelterzoo.domain.moneyLog.enums.MoneyLogExceptionEnum;
import com.noint.shelterzoo.domain.moneyLog.enums.MoneyType;
import com.noint.shelterzoo.domain.moneyLog.exception.MoneyLogException;
import com.noint.shelterzoo.domain.user.enums.MoneyUpdatePurpose;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MoneyLogAddRequestVO {
    private Long userSeq;
    private MoneyType moneyType;
    private BigDecimal money;
    private BigDecimal totalMoney;
    private MoneyUpdatePurpose purposeEnum;
    private Long adoptSeq;
    private Long supportLogSeq;
    private Long chargeLogSeq;

    public static MoneyLogAddRequestVO create(Long userSeq, MoneyType moneyType, BigDecimal money, BigDecimal totalMoney, MoneyUpdatePurpose purposeEnum, Long targetSeq) {
        MoneyLogAddRequestVO vo = new MoneyLogAddRequestVO();
        vo.setUserSeq(userSeq);
        vo.setMoneyType(moneyType);
        vo.setMoney(money);
        vo.setTotalMoney(totalMoney);
        vo.setPurposeEnum(purposeEnum);
        matchTargetSeq(vo, purposeEnum, targetSeq);

        return vo;
    }

    private static void matchTargetSeq(MoneyLogAddRequestVO vo, MoneyUpdatePurpose purposeEnum, Long targetSeq) {
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
