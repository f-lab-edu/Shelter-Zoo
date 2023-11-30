package com.noint.shelterzoo.service.charge;

import com.noint.shelterzoo.domain.charge.dto.req.ChargeMoneyRequestDTO;
import com.noint.shelterzoo.domain.charge.exception.ChargeException;
import com.noint.shelterzoo.domain.charge.repository.ChargeRepository;
import com.noint.shelterzoo.domain.charge.service.ChargeService;
import com.noint.shelterzoo.domain.charge.vo.req.ChargeLogRequestVO;
import com.noint.shelterzoo.domain.moneyLog.enums.MoneyTypeEnum;
import com.noint.shelterzoo.domain.moneyLog.service.MoneyLogService;
import com.noint.shelterzoo.domain.moneyLog.vo.req.MoneyLogInsertRequestVO;
import com.noint.shelterzoo.domain.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {ChargeService.class})
public class ChargeServiceUnitTest {
    @Autowired
    ChargeService chargeService;

    @MockBean
    UserService userService;
    @MockBean
    MoneyLogService moneyLogService;
    @MockBean
    ChargeRepository chargeRepository;

    @Test
    @DisplayName("유저 재화 충전 실패 : ChargeId 중복")
    void chargeMoneyFailByDuplicateChargeId() {
        // given
        Long userSeq = 17L;
        ChargeMoneyRequestDTO request = new ChargeMoneyRequestDTO();
        request.setChargeId(UUID.randomUUID().toString());
        request.setChargeAmount(BigDecimal.valueOf(50000));

        // when
        when(chargeRepository.countChargeId(request.getChargeId())).thenReturn(1);
        when(userService.getUserMoney(userSeq)).thenReturn(BigDecimal.valueOf(50000));
        doNothing().when(chargeRepository).insertChargeLog(any());
        doNothing().when(moneyLogService).insertLogByCharge(any());

        // then
        assertThrows(ChargeException.class, () -> chargeService.chargeMoney(userSeq, request));
    }

    @Test
    @DisplayName("유저 재화 충전 성공")
    void chargeMoneySuccess() {
        // given
        Long userSeq = 17L;
        ChargeMoneyRequestDTO request = new ChargeMoneyRequestDTO();
        request.setChargeId(UUID.randomUUID().toString());
        request.setChargeAmount(BigDecimal.valueOf(50000));

        // when
        when(chargeRepository.countChargeId(request.getChargeId())).thenReturn(0);
        when(userService.getUserMoney(userSeq)).thenReturn(BigDecimal.valueOf(50000));
        doNothing().when(chargeRepository).insertChargeLog(any());
        doNothing().when(moneyLogService).insertLogByCharge(any());

        // then
        chargeService.chargeMoney(userSeq, request);
        verify(chargeRepository, times(1)).countChargeId(request.getChargeId());
        verify(chargeRepository, times(1)).insertChargeLog(ChargeLogRequestVO.create(userSeq, request));
        verify(userService, times(1)).getUserMoney(userSeq);
        verify(moneyLogService, times(1)).insertLogByCharge(any());
    }
}
