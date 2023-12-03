package com.noint.shelterzoo.service.moneyLog;

import com.noint.shelterzoo.domain.moneyLog.enums.MoneyType;
import com.noint.shelterzoo.domain.moneyLog.repository.MoneyLogRepository;
import com.noint.shelterzoo.domain.moneyLog.service.MoneyLogService;
import com.noint.shelterzoo.domain.moneyLog.vo.req.MoneyLogInsertRequestVO;
import com.noint.shelterzoo.domain.user.enums.MoneyUpdatePurposeEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = {MoneyLogService.class})
public class MoneyLogServiceUnitTest {
    @Autowired
    MoneyLogService moneyLogService;

    @MockBean
    MoneyLogRepository moneyLogRepository;

    @Test
    @DisplayName("유기동물 입양 예약관련 재화 로그")
    void moneyLogInsertForAdoptReservation() {
        // given
        Long userSeq = 17L;
        BigDecimal totalMoney = BigDecimal.valueOf(50000);
        BigDecimal amount = BigDecimal.valueOf(50000);
        MoneyType moneyType = MoneyType.WITHDRAWAL;
        MoneyUpdatePurposeEnum purposeEnum = MoneyUpdatePurposeEnum.ADOPT_RESERVATION;
        Long targetTableSeq = 11L;
        MoneyLogInsertRequestVO request = MoneyLogInsertRequestVO.create(userSeq, moneyType, amount, totalMoney, purposeEnum, targetTableSeq);

        // when
        doNothing().when(moneyLogRepository).addMoneyLogAboutAdopt(any());

        // then
        moneyLogService.addMoneyLogAboutAdopt(request);

        verify(moneyLogRepository, times(1)).addMoneyLogAboutAdopt(request);
    }

    @Test
    @DisplayName("충전 후 재화 로그 작성")
    void insertMoneyLogByCharge() {
        // given
        Long userSeq = 17L;
        BigDecimal baseUserMoney = BigDecimal.valueOf(10000);
        BigDecimal chargeAmount = BigDecimal.valueOf(50000);
        BigDecimal totalMoney = baseUserMoney.add(chargeAmount);
        MoneyType moneyType = MoneyType.DEPOSIT;
        MoneyUpdatePurposeEnum purposeEnum = MoneyUpdatePurposeEnum.CHARGE;
        Long targetTableSeq = 11L;
        MoneyLogInsertRequestVO request = MoneyLogInsertRequestVO.create(userSeq, moneyType, chargeAmount, totalMoney, purposeEnum, targetTableSeq);

        // when
        doNothing().when(moneyLogRepository).addMoneyLogByCharge(any());

        // then
        moneyLogService.addMoneyLogByCharge(request);
        verify(moneyLogRepository, times(1)).addMoneyLogByCharge(request);
    }
}
