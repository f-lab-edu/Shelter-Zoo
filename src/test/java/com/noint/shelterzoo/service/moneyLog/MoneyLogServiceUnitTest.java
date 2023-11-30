package com.noint.shelterzoo.service.moneyLog;

import com.noint.shelterzoo.domain.moneyLog.enums.MoneyTypeEnum;
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
        MoneyTypeEnum moneyTypeEnum = MoneyTypeEnum.WITHDRAWAL;
        MoneyUpdatePurposeEnum purposeEnum = MoneyUpdatePurposeEnum.ADOPT_RESERVATION;
        Long targetTableSeq = 11L;

        // when
        doNothing().when(moneyLogRepository).moneyLogInsertForAdoptReservation(any());

        // then
        moneyLogService.insertLogAboutAdopt(userSeq, totalMoney, amount, moneyTypeEnum, purposeEnum, targetTableSeq);

        verify(moneyLogRepository, times(1)).moneyLogInsertForAdoptReservation(MoneyLogInsertRequestVO.createForAdoptReservation(userSeq, moneyTypeEnum, amount, totalMoney, purposeEnum, targetTableSeq));
    }
}
