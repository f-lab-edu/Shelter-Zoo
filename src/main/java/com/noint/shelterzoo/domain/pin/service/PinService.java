package com.noint.shelterzoo.domain.pin.service;

import com.noint.shelterzoo.domain.pin.enums.PinExceptionEnum;
import com.noint.shelterzoo.domain.pin.exception.PinException;
import com.noint.shelterzoo.domain.pin.repository.PinRepository;
import com.noint.shelterzoo.domain.pin.vo.req.PinUpRequestVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PinService {
    private final PinRepository pinRepository;

    public void pinUp(long userSeq, long petSeq) {
        try {
            pinRepository.pinUp(PinUpRequestVO.create(userSeq, petSeq));
        } catch (DataIntegrityViolationException e) {
            log.warn("관심 동물 추가 실패 : params : {userSeq : {}, petSeq : {}}", userSeq, petSeq);
            throw new PinException(PinExceptionEnum.DUPLICATED_PIN);
        }
    }

    public void pinUpDel(long userSeq, long petSeq) {
        pinRepository.pinUpDel(PinUpRequestVO.create(userSeq, petSeq));
    }
}
