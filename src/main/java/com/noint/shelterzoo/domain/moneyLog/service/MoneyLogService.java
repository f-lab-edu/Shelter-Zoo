package com.noint.shelterzoo.domain.moneyLog.service;

import com.noint.shelterzoo.domain.moneyLog.enums.MoneyTypeEnum;
import com.noint.shelterzoo.domain.moneyLog.repository.MoneyLogRepository;
import com.noint.shelterzoo.domain.moneyLog.vo.req.MoneyLogInsertRequestVO;
import com.noint.shelterzoo.domain.user.enums.MoneyUpdatePurposeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class MoneyLogService {
    private final MoneyLogRepository moneyLogRepository;

    public void moneyLogInsertForAdoptReservation(long userSeq, BigDecimal totalMoney, BigDecimal amount, MoneyTypeEnum moneyTypeEnum, MoneyUpdatePurposeEnum purposeEnum, long targetTableSeq) {
        MoneyLogInsertRequestVO requestVO = MoneyLogInsertRequestVO.createForAdoptReservation(userSeq, moneyTypeEnum, amount, totalMoney, purposeEnum, targetTableSeq);
        moneyLogRepository.moneyLogInsertForAdoptReservation(requestVO);
    }
}
