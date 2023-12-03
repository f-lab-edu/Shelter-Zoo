package com.noint.shelterzoo.domain.moneyLog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.noint.shelterzoo.domain.moneyLog.dto.res.MoneyLogListResponseDTO;
import com.noint.shelterzoo.domain.moneyLog.repository.MoneyLogRepository;
import com.noint.shelterzoo.domain.moneyLog.vo.req.MoneyLogAddRequestVO;
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
}
