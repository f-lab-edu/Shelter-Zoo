package com.noint.shelterzoo.service.pin;

import com.noint.shelterzoo.domain.pin.exception.PinException;
import com.noint.shelterzoo.domain.pin.repository.PinRepository;
import com.noint.shelterzoo.domain.pin.service.PinService;
import com.noint.shelterzoo.domain.pin.vo.req.PinUpRequestVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
        long userSeq = 17L;
        long petSeq = 955L;

        // when
        doNothing().when(pinRepository).pinUp(any());

        // then
        pinService.pinUp(userSeq, petSeq);

        verify(pinRepository, times(1)).pinUp(PinUpRequestVO.create(userSeq, petSeq));
    }

    @Test
    @DisplayName("관심 동물 등록 실패 : 중복")
    void pinUpFailByDuplicated() {
        // given
        long userSeq = 17L;
        long petSeq = 955L;

        // when
        doThrow(new DataIntegrityViolationException("중복 msg")).when(pinRepository).pinUp(any());

        // then
        assertThrows(PinException.class, () -> pinService.pinUp(userSeq, petSeq));
    }

    @Test
    @DisplayName("관심 동물 해제")
    void pinUpDel() {
        // given
        long userSeq = 17L;
        long petSeq = 955L;

        // when
        doNothing().when(pinRepository).pinUpDel(any());

        // then
        pinService.pinUpDel(userSeq, petSeq);

        verify(pinRepository, times(1)).pinUpDel(PinUpRequestVO.create(userSeq, petSeq));
    }
}
