package com.noint.shelterzoo.service.pin;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.noint.shelterzoo.domain.pin.dto.res.PinListResponseDTO;
import com.noint.shelterzoo.domain.pin.exception.PinException;
import com.noint.shelterzoo.domain.pin.repository.PinRepository;
import com.noint.shelterzoo.domain.pin.service.PinService;
import com.noint.shelterzoo.domain.pin.vo.req.PinListRequestVO;
import com.noint.shelterzoo.domain.pin.vo.req.PinUpRequestVO;
import com.noint.shelterzoo.domain.pin.vo.res.PinListResponseVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {PinService.class})
public class PinServiceUnitTest {
    @Autowired
    PinService pinService;

    @MockBean
    PinRepository pinRepository;


    @Test
    @DisplayName("관심 동물 등록")
    void pinUp() {
        // given
        Long userSeq = 17L;
        Long petSeq = 955L;

        // when
        doNothing().when(pinRepository).addPin(any());

        // then
        pinService.addPin(userSeq, petSeq);

        verify(pinRepository, times(1)).addPin(PinUpRequestVO.create(userSeq, petSeq));
    }

    @Test
    @DisplayName("관심 동물 등록 실패 : 중복")
    void pinUpFailByDuplicated() {
        // given
        Long userSeq = 17L;
        Long petSeq = 955L;

        // when
        doThrow(new DataIntegrityViolationException("중복 msg")).when(pinRepository).addPin(any());

        // then
        assertThrows(PinException.class, () -> pinService.addPin(userSeq, petSeq));
    }

    @Test
    @DisplayName("관심 동물 해제")
    void pinUpDel() {
        // given
        Long userSeq = 17L;
        Long petSeq = 955L;

        // when
        doNothing().when(pinRepository).delPin(any());

        // then
        pinService.delPin(userSeq, petSeq);

        verify(pinRepository, times(1)).delPin(PinUpRequestVO.create(userSeq, petSeq));
    }

    @Test
    @DisplayName("유기동물 페이지 리스트")
    void getAbandonedList() {
        // given
        Integer pageNum = 1;
        Integer pageSize = 20;
        Long userSeq = 17L;

        PageParam request = new PageParam();
        request.setPageNum(pageNum);
        request.setPageSize(pageSize);

        PinListResponseVO hopeValue = new PinListResponseVO();
        hopeValue.setSeq(955L);
        hopeValue.setThumbnail("http://www.animal.go.kr/files/shelter/2023/07/202310131510115_s.jpg");
        hopeValue.setKind("고양이");
        hopeValue.setKindDetail("스코티시폴드");
        hopeValue.setBirth("2018(년생)");
        hopeValue.setGender("M");
        hopeValue.setNeuter("Y");
        hopeValue.setNoticeEnd("2023-10-23 00:00:00");
        hopeValue.setIsReserve(true);

        List<PinListResponseVO> hopeValueList = new ArrayList<>();
        hopeValueList.add(hopeValue);

        // when
        when(pinRepository.getPinList(PinListRequestVO.create(userSeq, request))).thenReturn(hopeValueList);

        // then
        PageInfo<PinListResponseDTO> pageInfo = pinService.getPinList(userSeq, request);

        assertAll(
                () -> assertEquals(pageNum, pageInfo.getPageNum()),
                () -> assertEquals(pageSize, pageInfo.getPageSize())
        );
    }
}
