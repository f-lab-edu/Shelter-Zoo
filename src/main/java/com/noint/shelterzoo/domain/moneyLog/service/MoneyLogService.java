package com.noint.shelterzoo.domain.moneyLog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.noint.shelterzoo.domain.moneyLog.dto.res.MoneyDetailDTO;
import com.noint.shelterzoo.domain.moneyLog.dto.res.MoneyLogDetailWithAdoptResponseDTO;
import com.noint.shelterzoo.domain.moneyLog.dto.res.MoneyLogDetailWithChargeResponseDTO;
import com.noint.shelterzoo.domain.moneyLog.dto.res.MoneyLogListResponseDTO;
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

    public PageInfo<MoneyLogListResponseDTO> getMoneyLogList(Long userSeq, PageParam request) {
        PageInfo<MoneyLogListResponseVO> moneyLogList = PageHelper.startPage(request.getPageNum(), request.getPageSize())
                .doSelectPageInfo(() -> moneyLogRepository.getMoneyLogList(MoneyLogListRequestVO.create(userSeq, request)));
        return MoneyLogListResponseDTO.create(moneyLogList);
    }

    public MoneyDetailDTO getMoneyLogDetail(Long userSeq, Long moneyLogSeq) {
        MoneyLogDetailRequestVO voRequest = MoneyLogDetailRequestVO.create(userSeq, moneyLogSeq);
        String logType = moneyLogRepository.getMoneyLogType(voRequest);
        switch (MoneyUpdatePurpose.findEnumByStr(logType)) {
            case CHARGE:
                return MoneyLogDetailWithChargeResponseDTO.create(moneyLogRepository.getMoneyLogDetailWithCharge(voRequest));
//            case SUPPORT:
//                return MoneyLogDetailWithChargeResponseDTO.create(moneyLogRepository.getMoneyLogDetailWithCharge(voRequest));
            case ADOPT_PAYBACK:
            case ADOPT_RESERVATION:
                return MoneyLogDetailWithAdoptResponseDTO.create(moneyLogRepository.getMoneyLogDetailWithAdopt(voRequest));
            default:
                throw new MoneyLogException(MoneyLogExceptionEnum.UNKNOWN_LOG_TYPE);
        }
    }
}
