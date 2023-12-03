package com.noint.shelterzoo.domain.abandoned.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.noint.shelterzoo.domain.abandoned.dto.req.AbandonedListRequestDTO;
import com.noint.shelterzoo.domain.abandoned.dto.req.AdoptReservationRequestDTO;
import com.noint.shelterzoo.domain.abandoned.dto.req.AdoptUpdateRequestDTO;
import com.noint.shelterzoo.domain.abandoned.dto.res.AbandonedDetailResponseDTO;
import com.noint.shelterzoo.domain.abandoned.dto.res.AbandonedListResponseDTO;
import com.noint.shelterzoo.domain.abandoned.enums.AbandonedExceptionBody;
import com.noint.shelterzoo.domain.abandoned.enums.AdoptProcess;
import com.noint.shelterzoo.domain.abandoned.enums.PercentagePayBackPenalty;
import com.noint.shelterzoo.domain.abandoned.exception.AbandonedException;
import com.noint.shelterzoo.domain.abandoned.repository.AbandonedRepository;
import com.noint.shelterzoo.domain.abandoned.vo.req.AbandonedListRequestVO;
import com.noint.shelterzoo.domain.abandoned.vo.req.AdoptProcessUpdateRequestVO;
import com.noint.shelterzoo.domain.abandoned.vo.req.AdoptReservationRequestVO;
import com.noint.shelterzoo.domain.abandoned.vo.req.AdoptUpdateRequestVO;
import com.noint.shelterzoo.domain.abandoned.vo.res.AbandonedDetailResponseVO;
import com.noint.shelterzoo.domain.abandoned.vo.res.AbandonedListResponseVO;
import com.noint.shelterzoo.domain.abandoned.vo.res.AdoptCancelDateDiffResponseVO;
import com.noint.shelterzoo.domain.abandoned.vo.res.ReservationCheckResponseVO;
import com.noint.shelterzoo.domain.moneyLog.enums.MoneyType;
import com.noint.shelterzoo.domain.moneyLog.service.MoneyLogService;
import com.noint.shelterzoo.domain.moneyLog.vo.req.MoneyLogAddRequestVO;
import com.noint.shelterzoo.domain.user.enums.MoneyUpdatePurpose;
import com.noint.shelterzoo.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class AbandonedService {
    private final AbandonedRepository abandonedRepository;
    private final UserService userService;
    private final MoneyLogService moneyLogService;
    private final static BigDecimal RESERVATION_AMOUNT = BigDecimal.valueOf(50000);

    public PageInfo<AbandonedListResponseDTO> getAbandonedList(Long userSeq, AbandonedListRequestDTO request) {
        PageInfo<AbandonedListResponseVO> petsPageInfo = PageHelper.startPage(request.getPageNum(), request.getPageSize()).doSelectPageInfo(() -> abandonedRepository.getAbandonedList(AbandonedListRequestVO.create(userSeq, request)));
        return AbandonedListResponseDTO.create(petsPageInfo);
    }

    public AbandonedDetailResponseDTO getAbandonedPetDetail(Long petSeq) {
        AbandonedDetailResponseVO abandonedDetail = abandonedRepository.getAbandonedPetDetail(petSeq);
        if (abandonedDetail == null) {
            log.warn("유기동물 상세 페이지 가져오기 실패, params : {petSeq : {}}", petSeq);
            throw new AbandonedException(AbandonedExceptionBody.NO_CONTENT);
        }
        return AbandonedDetailResponseDTO.create(abandonedDetail);
    }

    @Transactional
    public void reservationPet(Long userSeq, AdoptReservationRequestDTO request) {
        boolean isAdoptAble = abandonedRepository.isAdoptAble(request.getPetSeq());
        if (!isAdoptAble) {
            log.warn("입양 예약 실패(예약 불가능한 상태), params : {userSeq : {}, request : {}}", userSeq, request);
            throw new AbandonedException(AbandonedExceptionBody.NOT_ADOPTABLE);
        }
        AdoptReservationRequestVO reservationRequest = AdoptReservationRequestVO.create(userSeq, request);
        abandonedRepository.reservationPet(reservationRequest);
        abandonedRepository.updateAdoptProcess(AdoptProcessUpdateRequestVO.create(request));

        BigDecimal userMoney = userService.getUserMoneyForUpdate(userSeq);
        BigDecimal updateUserMoney = userMoney.subtract(RESERVATION_AMOUNT);
        updateUserMoney(userSeq, userMoney, updateUserMoney, MoneyType.WITHDRAWAL, MoneyUpdatePurpose.ADOPT_RESERVATION, reservationRequest.getSeq());
    }

    private void updateUserMoney(Long userSeq, BigDecimal userMoney, BigDecimal totalMoney, MoneyType moneyType, MoneyUpdatePurpose purposeEnum, Long targetTableSeq) {
        BigDecimal amount = totalMoney.subtract(userMoney);
        if (totalMoney.compareTo(BigDecimal.ZERO) < 0) {
            log.warn("입양 예약 실패(재화 부족), params : {userSeq : {}, userMoney : {}, amount : {}}", userSeq, userMoney, RESERVATION_AMOUNT);
            throw new AbandonedException(AbandonedExceptionBody.LACK_OF_MONEY);
        }
        userService.updateUserMoney(userSeq, totalMoney);
        moneyLogService.addMoneyLogAboutAdopt(MoneyLogAddRequestVO.create(userSeq, moneyType, amount, totalMoney, purposeEnum, targetTableSeq));
    }

    @Transactional
    public void updateAdoptPet(Long userSeq, AdoptUpdateRequestDTO request) {
        AdoptUpdateRequestVO requestVO = AdoptUpdateRequestVO.create(userSeq, request);
        ReservationCheckResponseVO petReservationState = abandonedRepository.isReservationPet(requestVO);
        if (!isUpdateAble(petReservationState.getState())) {
            log.warn("입양 절차 수정 실패, params : {request : {}, nowAdoptState : {}}", request, petReservationState);
            throw new AbandonedException(AbandonedExceptionBody.NOT_UPDATABLE);
        }
        abandonedRepository.updateAdoptPet(requestVO);
        abandonedRepository.updateAdoptProcess(AdoptProcessUpdateRequestVO.create(request));
        adoptPetUpdatePayBack(requestVO, petReservationState.getAdoptSeq());
    }

    private boolean isUpdateAble(String adoptState) {
        return StringUtils.hasLength(adoptState) && adoptState.equals(AdoptProcess.RESERVATION.getStateStr());
    }

    private void adoptPetUpdatePayBack(AdoptUpdateRequestVO requestVO, long adoptSeq) {
        BigDecimal userMoney = userService.getUserMoneyForUpdate(requestVO.getUserSeq());
        BigDecimal updateUserMoney;
        switch (requestVO.getAdoptProcess()) {
            case ADOPTED:
                updateUserMoney = userMoney.add(RESERVATION_AMOUNT);
                break;
            case CANCEL:
                BigDecimal penaltyPayBack = payBackMoneyByAdoptCancel(requestVO);
                updateUserMoney = userMoney.add(penaltyPayBack);
                break;
            default:
                log.warn("입양 절차 취소/변경 이외의 값, params : {state : {}}", requestVO.getAdoptProcess());
                throw new AbandonedException(AbandonedExceptionBody.UNKNOWN_TYPE);
        }
        updateUserMoney(requestVO.getUserSeq(), userMoney, updateUserMoney, MoneyType.DEPOSIT, MoneyUpdatePurpose.ADOPT_PAYBACK, adoptSeq);
    }

    private BigDecimal payBackMoneyByAdoptCancel(AdoptUpdateRequestVO requestVO) {
        AdoptCancelDateDiffResponseVO dateDiffInfo = abandonedRepository.getDateDiffFromNow(requestVO);
        return payBackMoney(dateDiffInfo.getCreatedDiff(), dateDiffInfo.getNoticeEndDiff());
    }

    private BigDecimal payBackMoney(int createDiff, int noticeEndDiff) {
        if (isSameDay(createDiff)) {
            return RESERVATION_AMOUNT;
        }
        if (noticeEndDiff >= 5) {
            return PercentagePayBackPenalty.payBackWithPenalty(RESERVATION_AMOUNT, PercentagePayBackPenalty.DAY5);
        }
        if (noticeEndDiff >= 3) {
            return PercentagePayBackPenalty.payBackWithPenalty(RESERVATION_AMOUNT, PercentagePayBackPenalty.DAY3);
        }
        if (noticeEndDiff >= 1) {
            return PercentagePayBackPenalty.payBackWithPenalty(RESERVATION_AMOUNT, PercentagePayBackPenalty.DAY1);
        }
        return BigDecimal.ZERO;
    }

    private boolean isSameDay(int createDiff) {
        return createDiff > -1;
    }
}
