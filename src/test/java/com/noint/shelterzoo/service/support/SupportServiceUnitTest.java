package com.noint.shelterzoo.service.support;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.noint.shelterzoo.domain.moneyLog.service.MoneyLogService;
import com.noint.shelterzoo.domain.support.dto.req.DonateRequestDTO;
import com.noint.shelterzoo.domain.support.dto.res.SupportPetListResponseDTO;
import com.noint.shelterzoo.domain.support.exception.SupportException;
import com.noint.shelterzoo.domain.support.repository.SupportRepository;
import com.noint.shelterzoo.domain.support.service.SupportService;
import com.noint.shelterzoo.domain.support.vo.req.AddSupportLogRequestVO;
import com.noint.shelterzoo.domain.support.vo.req.UpdateSupportPetMoneyRequestVO;
import com.noint.shelterzoo.domain.support.vo.res.SupportMoneysResponseVO;
import com.noint.shelterzoo.domain.support.vo.res.SupportPetListResponseVO;
import com.noint.shelterzoo.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {SupportService.class})
public class SupportServiceUnitTest {
    @Autowired
    SupportService supportService;

    @MockBean
    SupportRepository supportRepository;
    @MockBean
    UserService userService;
    @MockBean
    MoneyLogService moneyLogService;

    @Test
    @DisplayName("후원이 필요한 동물 리스트")
    void getSupportPetList() {
        // given
        Integer pageNum = 1;
        Integer pageSize = 1;
        SupportPetListResponseVO hopeValue = new SupportPetListResponseVO();
        hopeValue.setSupportPetSeq(204L);
        hopeValue.setPetSeq(199L);
        hopeValue.setRequiredMoney(BigDecimal.valueOf(100000));
        hopeValue.setSupportedMoney(BigDecimal.valueOf(0));
        hopeValue.setDisease("골절");
        hopeValue.setCreatedAt("2023-12-04 16:49:08");
        hopeValue.setKind("개");
        hopeValue.setKindDetail("믹스견");
        hopeValue.setBirth("2023(년생)");
        hopeValue.setThumbnail("http://www.animal.go.kr/files/shelter/2023/07/202310161410103_s.jpg");
        List<SupportPetListResponseVO> hopeValueList = new ArrayList<>();
        hopeValueList.add(hopeValue);


        // when
        when(supportRepository.getSupportPetList(any())).thenReturn(hopeValueList);

        // then
        PageInfo<SupportPetListResponseDTO> supportPetList = supportService.getSupportPetList(new PageParam(pageNum, pageSize));
        assertEquals(pageNum, supportPetList.getPageNum());
    }

    @Test
    @DisplayName("후원 하기 성공")
    void donateSuccess() {
        // given
        Long userSeq = 17L;
        Long supportPetSeq = 501L;
        BigDecimal baseUserMoney = BigDecimal.valueOf(50000);
        BigDecimal donateAmount = BigDecimal.valueOf(10000);
        BigDecimal updatedUserMoney = baseUserMoney.subtract(donateAmount);
        DonateRequestDTO request = new DonateRequestDTO();
        request.setSupportPetSeq(supportPetSeq);
        request.setDonateAmount(donateAmount);

        SupportMoneysResponseVO supportMoneysResponse = new SupportMoneysResponseVO();
        supportMoneysResponse.setRequiredMoney(BigDecimal.valueOf(100000));
        supportMoneysResponse.setSupportedMoney(BigDecimal.valueOf(20000));

        // when
        when(supportRepository.getMoneysAboutSupport(supportPetSeq)).thenReturn(supportMoneysResponse);
        when(userService.getUserMoneyForUpdate(userSeq)).thenReturn(baseUserMoney);
        doNothing().when(supportRepository).updateSupportedMoney(any());
        doNothing().when(userService).updateUserMoney(userSeq, baseUserMoney.subtract(donateAmount));
        doNothing().when(supportRepository).addSupportLog(any());
        doNothing().when(moneyLogService).addMoneyLogBySupport(any());

        // then
        supportService.doDonate(userSeq, request);
        verify(supportRepository, timeout(1)).getMoneysAboutSupport(request.getSupportPetSeq());
        verify(supportRepository, timeout(1)).updateSupportedMoney(UpdateSupportPetMoneyRequestVO.create(supportMoneysResponse, request));
        verify(supportRepository, timeout(1)).addSupportLog(AddSupportLogRequestVO.create(userSeq, request));
        verify(userService, timeout(1)).updateUserMoney(userSeq, updatedUserMoney);
        verify(moneyLogService, timeout(1)).addMoneyLogBySupport(any());
    }

    @Test
    @DisplayName("후원 하기 실패 : 유저 재화 부족")
    void donateFailByLackOfMoney() {
        Long userSeq = 17L;
        Long supportPetSeq = 501L;
        BigDecimal baseUserMoney = BigDecimal.valueOf(10000);
        BigDecimal donateAmount = BigDecimal.valueOf(20000);
        DonateRequestDTO request = new DonateRequestDTO();
        request.setSupportPetSeq(supportPetSeq);
        request.setDonateAmount(donateAmount);

        SupportMoneysResponseVO supportMoneysResponse = new SupportMoneysResponseVO();
        supportMoneysResponse.setRequiredMoney(BigDecimal.valueOf(100000));
        supportMoneysResponse.setSupportedMoney(BigDecimal.valueOf(20000));

        // when
        when(supportRepository.getMoneysAboutSupport(supportPetSeq)).thenReturn(supportMoneysResponse);
        when(userService.getUserMoneyForUpdate(userSeq)).thenReturn(baseUserMoney);

        // then
        assertThrows(SupportException.class, () -> supportService.doDonate(userSeq, request));
    }

    @Test
    @DisplayName("후원 하기 실패 : 후원 금액 달성")
    void donateFailByGoalMoney() {
        Long userSeq = 17L;
        Long supportPetSeq = 501L;
        BigDecimal baseUserMoney = BigDecimal.valueOf(10000);
        BigDecimal donateAmount = BigDecimal.valueOf(20000);
        DonateRequestDTO request = new DonateRequestDTO();
        request.setSupportPetSeq(supportPetSeq);
        request.setDonateAmount(donateAmount);

        SupportMoneysResponseVO supportMoneysResponse = new SupportMoneysResponseVO();
        supportMoneysResponse.setRequiredMoney(BigDecimal.valueOf(100000));
        supportMoneysResponse.setSupportedMoney(BigDecimal.valueOf(100000));

        // when
        when(supportRepository.getMoneysAboutSupport(supportPetSeq)).thenReturn(supportMoneysResponse);

        // then
        assertThrows(SupportException.class, () -> supportService.doDonate(userSeq, request));
    }

    @Test
    @DisplayName("후원 하기 실패 : 후원 금액 초과")
    void donateFailBy() {
        Long userSeq = 17L;
        Long supportPetSeq = 501L;
        BigDecimal baseUserMoney = BigDecimal.valueOf(50000);
        BigDecimal donateAmount = BigDecimal.valueOf(20000);
        DonateRequestDTO request = new DonateRequestDTO();
        request.setSupportPetSeq(supportPetSeq);
        request.setDonateAmount(donateAmount);

        SupportMoneysResponseVO supportMoneysResponse = new SupportMoneysResponseVO();
        supportMoneysResponse.setRequiredMoney(BigDecimal.valueOf(100000));
        supportMoneysResponse.setSupportedMoney(BigDecimal.valueOf(90000));

        // when
        when(supportRepository.getMoneysAboutSupport(supportPetSeq)).thenReturn(supportMoneysResponse);

        // then
        assertThrows(SupportException.class, () -> supportService.doDonate(userSeq, request));
    }
}
