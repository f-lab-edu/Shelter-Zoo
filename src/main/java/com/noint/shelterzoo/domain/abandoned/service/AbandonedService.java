package com.noint.shelterzoo.domain.abandoned.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.noint.shelterzoo.domain.abandoned.dto.req.AbandonedListRequestDTO;
import com.noint.shelterzoo.domain.abandoned.dto.req.AdoptReservationRequestDTO;
import com.noint.shelterzoo.domain.abandoned.dto.req.AdoptUpdateRequestDTO;
import com.noint.shelterzoo.domain.abandoned.dto.res.AbandonedDetailResponseDTO;
import com.noint.shelterzoo.domain.abandoned.dto.res.AbandonedListResponseDTO;
import com.noint.shelterzoo.domain.abandoned.enums.AbandonedExceptionEnum;
import com.noint.shelterzoo.domain.abandoned.enums.AdoptCancelPayBackPenaltyEnum;
import com.noint.shelterzoo.domain.abandoned.enums.AdoptProcessEnum;
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
import com.noint.shelterzoo.domain.moneyLog.enums.MoneyTypeEnum;
import com.noint.shelterzoo.domain.user.enums.MoneyUpdatePurposeEnum;
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
    private final static BigDecimal RESERVATION_AMOUNT = BigDecimal.valueOf(50000);

    public PageInfo<AbandonedListResponseDTO> getAbandonedList(long userSeq, AbandonedListRequestDTO request) {
        PageInfo<AbandonedListResponseVO> petsPageInfo = PageHelper.startPage(request.getPageNum(), request.getPageSize()).doSelectPageInfo(() -> abandonedRepository.getAbandonedList(AbandonedListRequestVO.create(userSeq, request)));
        return AbandonedListResponseDTO.create(petsPageInfo);
    }

    public AbandonedDetailResponseDTO abandonedPetDetail(long petSeq) {
        AbandonedDetailResponseVO abandonedDetail = abandonedRepository.abandonedPetDetail(petSeq);
        if (abandonedDetail == null) {
            log.warn("유기동물 상세 페이지 가져오기 실패, params : {petSeq : {}}", petSeq);
            throw new AbandonedException(AbandonedExceptionEnum.NO_CONTENT);
        }
        return AbandonedDetailResponseDTO.create(abandonedDetail);
    }

    @Transactional
    public void adoptPetForReservation(long userSeq, AdoptReservationRequestDTO request) {
        boolean isAdoptAble = abandonedRepository.isAdoptAble(request.getPetSeq());
        if (!isAdoptAble) {
            log.warn("입양 예약 실패(예약 불가능한 상태), params : {userSeq : {}, request : {}}", userSeq, request);
            throw new AbandonedException(AbandonedExceptionEnum.NOT_ADOPTABLE);
        }
        AdoptReservationRequestVO reservationRequest = AdoptReservationRequestVO.create(userSeq, request);
        abandonedRepository.adoptPetForReservation(reservationRequest);
        abandonedRepository.adoptProcessUpdate(AdoptProcessUpdateRequestVO.create(request));

        BigDecimal userMoney = userService.getUserMoney(userSeq);
        BigDecimal updateUserMoney = userMoney.subtract(RESERVATION_AMOUNT);
        userMoneyUpdate(userSeq, userMoney, updateUserMoney, MoneyTypeEnum.WITHDRAWAL, MoneyUpdatePurposeEnum.ADOPT_RESERVATION, reservationRequest.getSeq());
    }

    private void userMoneyUpdate(long userSeq, BigDecimal userMoney, BigDecimal totalMoney, MoneyTypeEnum moneyTypeEnum, MoneyUpdatePurposeEnum purposeEnum, long targetTableSeq) {
        BigDecimal amount = totalMoney.subtract(userMoney);
        if (totalMoney.compareTo(BigDecimal.ZERO) < 0) {
            log.warn("입양 예약 실패(재화 부족), params : {userSeq : {}, userMoney : {}, amount : {}}", userSeq, userMoney, RESERVATION_AMOUNT);
            throw new AbandonedException(AbandonedExceptionEnum.LACK_OF_MONEY);
        }
        userService.userMoneyUpdate(userSeq, totalMoney, amount, moneyTypeEnum, purposeEnum, targetTableSeq);
    }

    @Transactional
    public void adoptPetUpdate(long userSeq, AdoptUpdateRequestDTO request) {
        AdoptUpdateRequestVO requestVO = AdoptUpdateRequestVO.create(userSeq, request);
        ReservationCheckResponseVO petReservationState = abandonedRepository.isReservationPet(requestVO);
        if (!this.isUpdateAble(petReservationState.getState())) {
            log.warn("입양 절차 수정 실패, params : {request : {}, nowAdoptState : {}}", request, petReservationState);
            throw new AbandonedException(AbandonedExceptionEnum.NOT_UPDATABLE);
        }
        abandonedRepository.adoptPetUpdate(requestVO);
        abandonedRepository.adoptProcessUpdate(AdoptProcessUpdateRequestVO.create(request));
        this.adoptPetUpdatePayBack(requestVO, petReservationState.getAdoptSeq());
    }

    private boolean isUpdateAble(String adoptState) {
        return StringUtils.hasLength(adoptState) && adoptState.equals(AdoptProcessEnum.RESERVATION.getStateStr());
    }

    private void adoptPetUpdatePayBack(AdoptUpdateRequestVO requestVO, long adoptSeq) {
        BigDecimal userMoney = userService.getUserMoney(requestVO.getUserSeq());
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
                throw new AbandonedException(AbandonedExceptionEnum.UNKNOWN_TYPE);
        }
        this.userMoneyUpdate(requestVO.getUserSeq(), userMoney, updateUserMoney, MoneyTypeEnum.DEPOSIT, MoneyUpdatePurposeEnum.ADOPT_PAYBACK, adoptSeq);
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
            return AdoptCancelPayBackPenaltyEnum.payBackWithPenalty(RESERVATION_AMOUNT, AdoptCancelPayBackPenaltyEnum.DAY5);
        }
        if (noticeEndDiff >= 3) {
            return AdoptCancelPayBackPenaltyEnum.payBackWithPenalty(RESERVATION_AMOUNT, AdoptCancelPayBackPenaltyEnum.DAY3);
        }
        if (noticeEndDiff >= 1) {
            return AdoptCancelPayBackPenaltyEnum.payBackWithPenalty(RESERVATION_AMOUNT, AdoptCancelPayBackPenaltyEnum.DAY1);
        }
        return BigDecimal.ZERO;
    }

    private boolean isSameDay(int createDiff) {
        return createDiff > -1;
    }

    public void pinUp(long userSeq, long petSeq) {
        try {
            abandonedRepository.pinUp(PinUpRequestVO.create(userSeq, petSeq));
        } catch (DataIntegrityViolationException e) {
            log.warn("관심 동물 추가 실패 : params : {userSeq : {}, petSeq : {}}", userSeq, petSeq);
            throw new AbandonedException(AbandonedExceptionEnum.DUPLICATED_PIN);
        }
    }

    public void pinUpDel(long userSeq, long petSeq) {
        abandonedRepository.pinUpDel(PinUpRequestVO.create(userSeq, petSeq));
    }
}
