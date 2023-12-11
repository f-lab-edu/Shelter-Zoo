package com.noint.shelterzoo.domain.moneyLog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.noint.shelterzoo.domain.moneyLog.dto.res.*;
import com.noint.shelterzoo.domain.moneyLog.enums.MoneyLogExceptionEnum;
import com.noint.shelterzoo.domain.moneyLog.enums.MoneyUpdatePurpose;
import com.noint.shelterzoo.domain.moneyLog.exception.MoneyLogException;
import com.noint.shelterzoo.domain.moneyLog.repository.MoneyLogRepository;
import com.noint.shelterzoo.domain.moneyLog.vo.req.MoneyLogAddRequestVO;
import com.noint.shelterzoo.domain.moneyLog.vo.req.MoneyLogDetailRequestVO;
import com.noint.shelterzoo.domain.moneyLog.vo.req.MoneyLogListRequestVO;
import com.noint.shelterzoo.domain.moneyLog.vo.res.MoneyLogListResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MoneyLogService {
    private final MoneyLogRepository moneyLogRepository;

    public void addMoneyLogAboutAdopt(MoneyLogAddRequestVO request) {
        moneyLogRepository.addMoneyLogAboutAdopt(request);
    }

    public void addMoneyLogByCharge(MoneyLogAddRequestVO request) {
        moneyLogRepository.addMoneyLogByCharge(request);
    }

    public void addMoneyLogBySupport(MoneyLogAddRequestVO requestVO) {
        moneyLogRepository.addMoneyLogBySupport(requestVO);
    }

    @Transactional(readOnly = true)
    public PageInfo<MoneyLogListResponseDTO> getMoneyLogList(Long userSeq, PageParam request) {
        PageInfo<MoneyLogListResponseVO> moneyLogList = PageHelper.startPage(request.getPageNum(), request.getPageSize())
                .doSelectPageInfo(() -> moneyLogRepository.getMoneyLogList(MoneyLogListRequestVO.create(userSeq, request)));
        return MoneyLogListResponseDTO.create(moneyLogList);
    }

    @Transactional(readOnly = true)
    public MoneyLogDetailDTO getMoneyLogDetail(Long userSeq, Long moneyLogSeq) {
        MoneyLogDetailRequestVO voRequest = MoneyLogDetailRequestVO.create(userSeq, moneyLogSeq);
        String logType = moneyLogRepository.getMoneyLogType(voRequest);
        switch (MoneyUpdatePurpose.findEnumByStr(logType)) {
            case CHARGE:
                return MoneyLogDetailWithChargeResponseDTO.create(moneyLogRepository.getMoneyLogDetailWithCharge(voRequest));
            case SUPPORT:
                return MoneyLogDetailWithSupportResponseDTO.create(moneyLogRepository.getMoneyLogDetailWithSupport(voRequest));
            case ADOPT_PAYBACK:
            case ADOPT_RESERVATION:
                return MoneyLogDetailWithAdoptResponseDTO.create(moneyLogRepository.getMoneyLogDetailWithAdopt(voRequest));
            default:
                throw new MoneyLogException(MoneyLogExceptionEnum.UNKNOWN_LOG_TYPE);
        }
    }
}
