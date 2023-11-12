package com.noint.shelterzoo.domain.abandoned.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.noint.shelterzoo.domain.abandoned.dto.req.AbandonedListRequestDTO;
import com.noint.shelterzoo.domain.abandoned.dto.req.AdoptReservationRequestDTO;
import com.noint.shelterzoo.domain.abandoned.dto.req.AdoptUpdateRequestDTO;
import com.noint.shelterzoo.domain.abandoned.dto.res.AbandonedDetailResponseDTO;
import com.noint.shelterzoo.domain.abandoned.dto.res.AbandonedListResponseDTO;
import com.noint.shelterzoo.domain.abandoned.enums.AbandonedExceptionEnum;
import com.noint.shelterzoo.domain.abandoned.enums.AdoptProcessEnum;
import com.noint.shelterzoo.domain.abandoned.exception.AbandonedException;
import com.noint.shelterzoo.domain.abandoned.repository.AbandonedRepository;
import com.noint.shelterzoo.domain.abandoned.vo.req.AbandonedListRequestVO;
import com.noint.shelterzoo.domain.abandoned.vo.req.AdoptProcessUpdateRequestVO;
import com.noint.shelterzoo.domain.abandoned.vo.req.AdoptReservationRequestVO;
import com.noint.shelterzoo.domain.abandoned.vo.req.AdoptUpdateRequestVO;
import com.noint.shelterzoo.domain.abandoned.vo.res.AbandonedDetailResponseVO;
import com.noint.shelterzoo.domain.abandoned.vo.res.AbandonedListResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class AbandonedService {
    private final AbandonedRepository abandonedRepository;

    public PageInfo<AbandonedListResponseDTO> getAbandonedList(long userSeq, AbandonedListRequestDTO request) {
        PageInfo<AbandonedListResponseVO> petsPageInfo = PageHelper.startPage(request.getPageNum(), request.getPageSize())
                .doSelectPageInfo(() -> abandonedRepository.getAbandonedList(AbandonedListRequestVO.create(userSeq, request)));
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
            log.warn("입양 예약 실패, params : {userSeq : {}, request : {}}", userSeq, request);
            throw new AbandonedException(AbandonedExceptionEnum.NOT_ADOPTABLE);
        }
        abandonedRepository.adoptPetForReservation(AdoptReservationRequestVO.create(userSeq, request));
        abandonedRepository.adoptProcessUpdate(AdoptProcessUpdateRequestVO.create(request));
    }

    @Transactional
    public void adoptPetUpdate(long userSeq, AdoptUpdateRequestDTO request) {
        AdoptUpdateRequestVO requestVO = AdoptUpdateRequestVO.create(userSeq, request);
        String adoptState = abandonedRepository.isReservationPet(requestVO);
        if (!this.isUpdateAble(adoptState)) {
            log.warn("입양 절차 수정 실패, params : {request : {}, nowAdoptState : {}}", request, adoptState);
            throw new AbandonedException(AbandonedExceptionEnum.NOT_UPDATABLE);
        }
        abandonedRepository.adoptPetUpdate(requestVO);
        abandonedRepository.adoptProcessUpdate(AdoptProcessUpdateRequestVO.create(request));
    }

    private boolean isUpdateAble(String adoptState) {
        return StringUtils.hasLength(adoptState) && adoptState.equals(AdoptProcessEnum.RESERVATION.getStateStr());
    }
}
