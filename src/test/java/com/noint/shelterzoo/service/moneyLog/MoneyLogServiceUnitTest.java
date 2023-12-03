package com.noint.shelterzoo.service.moneyLog;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.noint.shelterzoo.domain.moneyLog.dto.res.MoneyDetailDTO;
import com.noint.shelterzoo.domain.moneyLog.dto.res.MoneyLogListResponseDTO;
import com.noint.shelterzoo.domain.moneyLog.enums.MoneyType;
import com.noint.shelterzoo.domain.moneyLog.repository.MoneyLogRepository;
import com.noint.shelterzoo.domain.moneyLog.service.MoneyLogService;
import com.noint.shelterzoo.domain.moneyLog.vo.req.MoneyLogAddRequestVO;
import com.noint.shelterzoo.domain.moneyLog.vo.res.MoneyLogDetailWithAdoptResVO;
import com.noint.shelterzoo.domain.moneyLog.vo.res.MoneyLogListResponseVO;
import com.noint.shelterzoo.domain.user.enums.MoneyUpdatePurpose;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        MoneyUpdatePurpose purposeEnum = MoneyUpdatePurpose.ADOPT_RESERVATION;
        Long targetTableSeq = 11L;
        MoneyLogAddRequestVO request = MoneyLogAddRequestVO.create(userSeq, moneyType, amount, totalMoney, purposeEnum, targetTableSeq);

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
        MoneyUpdatePurpose purposeEnum = MoneyUpdatePurpose.CHARGE;
        Long targetTableSeq = 11L;
        MoneyLogAddRequestVO request = MoneyLogAddRequestVO.create(userSeq, moneyType, chargeAmount, totalMoney, purposeEnum, targetTableSeq);

        // when
        doNothing().when(moneyLogRepository).addMoneyLogByCharge(any());

        // then
        moneyLogService.addMoneyLogByCharge(request);
        verify(moneyLogRepository, times(1)).addMoneyLogByCharge(request);
    }

    @Test
    @DisplayName("유저 재화로그 리스트 가져오기")
    void getMoneyLogList() {
        // given
        Long userSeq = 17L;
        Integer pageNum = 1;
        Integer pageSize = 10;
        PageParam request = new PageParam(pageNum, pageSize);
        MoneyLogListResponseVO hopeValue = new MoneyLogListResponseVO();
        hopeValue.setMoneyLogSeq(41L);
        hopeValue.setUserSeq(17L);
        hopeValue.setMoneyType("입금");
        hopeValue.setMoney(BigDecimal.valueOf(50000));
        hopeValue.setTotalMoney(BigDecimal.valueOf(375000));
        hopeValue.setAdoptSeq(null);
        hopeValue.setSupportLogSeq(null);
        hopeValue.setChargeLogSeq(null);
        hopeValue.setCreatedAt("2023-12-02 19:34:35");

        List<MoneyLogListResponseVO> hopeValueList = new ArrayList<>();
        hopeValueList.add(hopeValue);
        // when
        when(moneyLogRepository.getMoneyLogList(any())).thenReturn(hopeValueList);

        // then
        PageInfo<MoneyLogListResponseDTO> moneyLogList = moneyLogService.getMoneyLogList(userSeq, request);
        assertAll(
                () -> assertEquals(pageNum, moneyLogList.getPageNum()),
                () -> assertEquals(pageSize, moneyLogList.getPageSize())
        );
    }

    @Test
    @DisplayName("예약관련 재화 로그 상세보기")
    void getMoneyLogDetailWithAdopt() {
        // given
        Long userSeq = 17L;
        Long moneyLogSeq = 35L;

        MoneyLogDetailWithAdoptResVO hopeValue = new MoneyLogDetailWithAdoptResVO();
        hopeValue.setMoneyLogSeq(moneyLogSeq);
        hopeValue.setUserSeq(userSeq);
        hopeValue.setMoneyType("출금");
        hopeValue.setMoney(BigDecimal.valueOf(-50000));
        hopeValue.setTotalMoney(BigDecimal.valueOf(75000));
        hopeValue.setType("예약보증");
        hopeValue.setCreatedAt("2023-11-27 06:57:00");
        hopeValue.setAdoptState("취소");
        hopeValue.setPetSeq(952L);
        hopeValue.setPetThumbnail("http://www.animal.go.kr/files/shelter/2023/07/202310131710431_s.jpg");

        // when
        when(moneyLogRepository.getMoneyLogType(any())).thenReturn("예약보증");
        when(moneyLogRepository.getMoneyLogDetailWithAdopt(any())).thenReturn(hopeValue);

        // then
        MoneyDetailDTO moneyLogDetail = moneyLogService.getMoneyLogDetail(userSeq, moneyLogSeq);
    }
}
