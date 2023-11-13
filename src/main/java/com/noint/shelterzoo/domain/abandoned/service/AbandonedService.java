package com.noint.shelterzoo.domain.abandoned.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.noint.shelterzoo.domain.abandoned.dto.req.AbandonedListRequestDTO;
import com.noint.shelterzoo.domain.abandoned.dto.res.AbandonedDetailResponseDTO;
import com.noint.shelterzoo.domain.abandoned.dto.res.AbandonedListResponseDTO;
import com.noint.shelterzoo.domain.abandoned.enums.AbandonedExceptionEnum;
import com.noint.shelterzoo.domain.abandoned.exception.AbandonedException;
import com.noint.shelterzoo.domain.abandoned.repository.AbandonedRepository;
import com.noint.shelterzoo.domain.abandoned.vo.req.AbandonedListRequestVO;
import com.noint.shelterzoo.domain.abandoned.vo.res.AbandonedDetailResponseVO;
import com.noint.shelterzoo.domain.abandoned.vo.res.AbandonedListResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
