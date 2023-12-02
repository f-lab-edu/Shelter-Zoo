package com.noint.shelterzoo.domain.pin.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.noint.shelterzoo.domain.pin.dto.res.PinListResponseDTO;
import com.noint.shelterzoo.domain.pin.enums.PinExceptionEnum;
import com.noint.shelterzoo.domain.pin.exception.PinException;
import com.noint.shelterzoo.domain.pin.repository.PinRepository;
import com.noint.shelterzoo.domain.pin.vo.req.PinListRequestVO;
import com.noint.shelterzoo.domain.pin.vo.req.PinUpRequestVO;
import com.noint.shelterzoo.domain.pin.vo.res.PinListResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PinService {
    private final PinRepository pinRepository;

    public void pinUp(Long userSeq, Long petSeq) {
        try {
            pinRepository.pinUp(PinUpRequestVO.create(userSeq, petSeq));
        } catch (DataIntegrityViolationException e) {
            log.warn("관심 동물 추가 실패 : params : {userSeq : {}, petSeq : {}}", userSeq, petSeq);
            throw new PinException(PinExceptionEnum.DUPLICATED_PIN);
        }
    }

    public void pinUpDel(Long userSeq, Long petSeq) {
        pinRepository.pinUpDel(PinUpRequestVO.create(userSeq, petSeq));
    }

    public PageInfo<PinListResponseDTO> getPinupList(Long userSeq, PageParam request) {
        PageInfo<PinListResponseVO> petsPageInfo = PageHelper.startPage(request.getPageNum(), request.getPageSize())
                .doSelectPageInfo(() -> pinRepository.getPinupList(PinListRequestVO.create(userSeq, request)));
        return PinListResponseDTO.create(petsPageInfo);
    }
}
