package com.noint.shelterzoo.service.abandoned;

import com.github.pagehelper.PageInfo;
import com.noint.shelterzoo.domain.abandoned.dto.req.AbandonedListRequestDTO;
import com.noint.shelterzoo.domain.abandoned.dto.req.AdoptReservationRequestDTO;
import com.noint.shelterzoo.domain.abandoned.dto.req.AdoptUpdateRequestDTO;
import com.noint.shelterzoo.domain.abandoned.dto.res.AbandonedDetailResponseDTO;
import com.noint.shelterzoo.domain.abandoned.dto.res.AbandonedListResponseDTO;
import com.noint.shelterzoo.domain.abandoned.exception.AbandonedException;
import com.noint.shelterzoo.domain.abandoned.repository.AbandonedRepository;
import com.noint.shelterzoo.domain.abandoned.service.AbandonedService;
import com.noint.shelterzoo.domain.abandoned.vo.req.AbandonedListRequestVO;
import com.noint.shelterzoo.domain.abandoned.vo.req.AdoptProcessUpdateRequestVO;
import com.noint.shelterzoo.domain.abandoned.vo.req.AdoptReservationRequestVO;
import com.noint.shelterzoo.domain.abandoned.vo.req.AdoptUpdateRequestVO;
import com.noint.shelterzoo.domain.abandoned.vo.res.AbandonedDetailResponseVO;
import com.noint.shelterzoo.domain.abandoned.vo.res.AbandonedListResponseVO;
import com.noint.shelterzoo.domain.abandoned.vo.res.AdoptCancelDateDiffResponseVO;
import com.noint.shelterzoo.domain.abandoned.vo.res.ReservationCheckResponseVO;
import com.noint.shelterzoo.domain.moneyLog.service.MoneyLogService;
import com.noint.shelterzoo.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {AbandonedService.class})
public class AbandonedServiceUnitTest {
    @Autowired
    AbandonedService abandonedService;

    @MockBean
    AbandonedRepository abandonedRepository;
    @MockBean
    UserService userService;
    @MockBean
    MoneyLogService moneyLogService;

    @Test
    @DisplayName("유기동물 페이지 리스트")
    void getAbandonedList() {
        // given
        Integer pageNum = 1;
        Integer pageSize = 20;
        Long userSeq = 17L;
        AbandonedListRequestDTO request = new AbandonedListRequestDTO();
        request.setLocation("서울");
        request.setKind("고양이");
        request.setGender("전체");
        request.setNeuter("전체");
        request.setPageNum(pageNum);
        request.setPageSize(pageSize);

        AbandonedListResponseVO hopeValue = new AbandonedListResponseVO();
        hopeValue.setSeq(955L);
        hopeValue.setThumbnail("http://www.animal.go.kr/files/shelter/2023/07/202310131510115_s.jpg");
        hopeValue.setKind("고양이");
        hopeValue.setKindDetail("스코티시폴드");
        hopeValue.setBirth("2018(년생)");
        hopeValue.setGender("M");
        hopeValue.setNeuter("Y");
        hopeValue.setNoticeEnd("2023-10-23 00:00:00");
        hopeValue.setIsPin(true);

        List<AbandonedListResponseVO> hopeValueList = new ArrayList<>();
        hopeValueList.add(hopeValue);

        // when
        when(abandonedRepository.getAbandonedList(AbandonedListRequestVO.create(userSeq, request))).thenReturn(hopeValueList);

        // then
        PageInfo<AbandonedListResponseDTO> pageInfo = abandonedService.getAbandonedList(userSeq, request);

        assertAll(
                () -> assertEquals(pageNum, pageInfo.getPageNum()),
                () -> assertEquals(pageSize, pageInfo.getPageSize())
        );
    }

    @Test
    @DisplayName("유기동물 상세 페이지")
    void abandonedPetDetail() {
        // given
        Long petSeq = 955L;

        AbandonedDetailResponseVO hopeValue = new AbandonedDetailResponseVO();
        hopeValue.setSeq(955L);
        hopeValue.setHappenPlace("경상북도 포항시 북구 장량중앙로 117 포항장량5단지LH아파트");
        hopeValue.setKind("고양이");
        hopeValue.setKindDetail(" 스코티시폴드");
        hopeValue.setColor("황백");
        hopeValue.setBirth("2018(년생)");
        hopeValue.setWeight("5.6(Kg)");
        hopeValue.setNoticeEnd("2023-10-23 00:00:00");
        hopeValue.setGender("M");
        hopeValue.setNeuter("Y");
        hopeValue.setSpecialMark("5세 이상 추정, 매우 온순함, 깨끗하게 관리되어있음, 뱃살이 쳐져있는 체형,빨리 찾으러오세요~");
        hopeValue.setImg("http://www.animal.go.kr/files/shelter/2023/07/202310131510115.jpg");
        hopeValue.setAdoptProcess(null);
        hopeValue.setShelterName("서울강북보호소");
        hopeValue.setShelterTel("02-0000-0001");
        hopeValue.setShelterAddr("서울시 강북구 ㅁㅁ로 01길 01");

        // when
        when(abandonedRepository.getAbandonedPetDetail(petSeq)).thenReturn(hopeValue);

        // then
        AbandonedDetailResponseDTO abandonedDetail = abandonedService.getAbandonedPetDetail(petSeq);

        assertEquals(abandonedDetail, AbandonedDetailResponseDTO.create(hopeValue));
    }

    @Test
    @DisplayName("유기동물 상세 페이지 실패 : 컨텐츠 없음")
    void abandonedDetailFail() {
        // given
        Long petSeq = 0L;

        // when
        when(abandonedRepository.getAbandonedPetDetail(petSeq)).thenReturn(null);

        // then
        assertThrows(AbandonedException.class, () -> abandonedService.getAbandonedPetDetail(petSeq));
    }

    @Test
    @DisplayName("입양 예약 성공")
    void adoptReservationSuccess() {
        // given
        Long userSeq = 17L;
        AdoptReservationRequestDTO request = new AdoptReservationRequestDTO();
        request.setPetSeq(955L);
        request.setVisitDate("2023-11-20");

        // when
        when(abandonedRepository.isAdoptAble(request.getPetSeq())).thenReturn(Boolean.TRUE);
        doNothing().when(abandonedRepository).reservationPet(any());
        doNothing().when(abandonedRepository).updateAdoptProcess(any());
        when(userService.getUserMoneyForUpdate(userSeq)).thenReturn(BigDecimal.valueOf(100000L));

        // then
        abandonedService.reservationPet(userSeq, request);

        verify(abandonedRepository, times(1)).isAdoptAble(request.getPetSeq());
        verify(abandonedRepository, times(1)).reservationPet(AdoptReservationRequestVO.create(userSeq, request));
        verify(abandonedRepository, times(1)).updateAdoptProcess(AdoptProcessUpdateRequestVO.create(request));
    }

    @Test
    @DisplayName("입양 예약 실패 : 재화 부족")
    void adoptReservationFailByLackOfMoney() {
        // given
        Long userSeq = 17L;
        AdoptReservationRequestDTO request = new AdoptReservationRequestDTO();
        request.setPetSeq(955L);
        request.setVisitDate("2023-11-20");

        // when
        when(abandonedRepository.isAdoptAble(request.getPetSeq())).thenReturn(Boolean.TRUE);
        doNothing().when(abandonedRepository).reservationPet(any());
        doNothing().when(abandonedRepository).updateAdoptProcess(any());
        when(userService.getUserMoneyForUpdate(userSeq)).thenReturn(BigDecimal.valueOf(10000L));

        // then
        assertThrows(AbandonedException.class, () -> abandonedService.reservationPet(userSeq, request));
    }

    @Test
    @DisplayName("입양 예약 실패 : 입양 불가능 상태")
    void adoptReservationFailByNotAdoptAble() {
        // given
        Long userSeq = 17L;
        AdoptReservationRequestDTO request = new AdoptReservationRequestDTO();
        request.setPetSeq(955L);
        request.setVisitDate("2023-11-20");

        // when
        when(abandonedRepository.isAdoptAble(request.getPetSeq())).thenReturn(Boolean.FALSE);
        doNothing().when(abandonedRepository).reservationPet(any());
        doNothing().when(abandonedRepository).updateAdoptProcess(any());

        // then
        assertThrows(AbandonedException.class, () -> abandonedService.reservationPet(userSeq, request));
    }

    @Test
    @DisplayName("입양 예약 TO 취소 변경")
    void adoptUpdateSuccessToCancel() {
        // given
        Long userSeq = 17L;
        AdoptUpdateRequestDTO request = new AdoptUpdateRequestDTO();
        request.setPetSeq(955L);
        request.setState("취소");

        ReservationCheckResponseVO checkHopeValue = new ReservationCheckResponseVO();
        checkHopeValue.setAdoptSeq(11L);
        checkHopeValue.setState("예약");

        AdoptCancelDateDiffResponseVO diffHopeValue = new AdoptCancelDateDiffResponseVO();
        diffHopeValue.setCreatedDiff(-1);
        diffHopeValue.setNoticeEndDiff(3);

        // when
        when(abandonedRepository.isReservationPet(any())).thenReturn(checkHopeValue);
        doNothing().when(abandonedRepository).updateAdoptPet(any());
        doNothing().when(abandonedRepository).updateAdoptProcess(any());
        when(abandonedRepository.getDateDiffFromNow(any())).thenReturn(diffHopeValue);
        when(userService.getUserMoneyForUpdate(userSeq)).thenReturn(BigDecimal.valueOf(100000));

        // then
        abandonedService.updateAdoptPet(userSeq, request);

        verify(abandonedRepository, times(1)).isReservationPet(AdoptUpdateRequestVO.create(userSeq, request));
        verify(abandonedRepository, times(1)).updateAdoptPet(AdoptUpdateRequestVO.create(userSeq, request));
        verify(abandonedRepository, times(1)).updateAdoptProcess(AdoptProcessUpdateRequestVO.create(request));
    }

    @Test
    @DisplayName("입양 예약 TO 확정 변경")
    void adoptUpdateSuccessToConfirm() {
        // given
        Long userSeq = 17L;
        AdoptUpdateRequestDTO request = new AdoptUpdateRequestDTO();
        request.setPetSeq(955L);
        request.setState("입양");

        ReservationCheckResponseVO checkHopeValue = new ReservationCheckResponseVO();
        checkHopeValue.setAdoptSeq(11L);
        checkHopeValue.setState("예약");

        AdoptCancelDateDiffResponseVO diffHopeValue = new AdoptCancelDateDiffResponseVO();
        diffHopeValue.setCreatedDiff(-1);
        diffHopeValue.setNoticeEndDiff(3);

        // when
        when(abandonedRepository.isReservationPet(any())).thenReturn(checkHopeValue);
        doNothing().when(abandonedRepository).updateAdoptPet(any());
        doNothing().when(abandonedRepository).updateAdoptProcess(any());
        when(abandonedRepository.getDateDiffFromNow(any())).thenReturn(diffHopeValue);
        when(userService.getUserMoneyForUpdate(userSeq)).thenReturn(BigDecimal.valueOf(100000));

        // then
        abandonedService.updateAdoptPet(userSeq, request);

        verify(abandonedRepository, times(1)).isReservationPet(AdoptUpdateRequestVO.create(userSeq, request));
        verify(abandonedRepository, times(1)).updateAdoptPet(AdoptUpdateRequestVO.create(userSeq, request));
        verify(abandonedRepository, times(1)).updateAdoptProcess(AdoptProcessUpdateRequestVO.create(request));
    }

    @Test
    @DisplayName("입양 예약 변경 실패")
    void adoptUpdateFail() {
        // given
        Long userSeq = 17L;
        AdoptUpdateRequestDTO request = new AdoptUpdateRequestDTO();
        request.setPetSeq(955L);
        request.setState("취소");

        ReservationCheckResponseVO hopeValue = new ReservationCheckResponseVO();
        hopeValue.setAdoptSeq(11L);
        hopeValue.setState("취소");

        // when
        when(abandonedRepository.isReservationPet(any())).thenReturn(hopeValue);
        doNothing().when(abandonedRepository).updateAdoptPet(any());
        doNothing().when(abandonedRepository).updateAdoptProcess(any());

        // then
        assertThrows(AbandonedException.class, () -> abandonedService.updateAdoptPet(userSeq, request));
    }
}
