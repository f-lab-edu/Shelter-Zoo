package com.noint.shelterzoo.domain.moneyLog.service;

import com.noint.shelterzoo.domain.moneyLog.repository.MoneyLogRepository;
import com.noint.shelterzoo.domain.moneyLog.vo.req.MoneyLogInsertRequestVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MoneyLogService {
    private final MoneyLogRepository moneyLogRepository;

    public void addMoneyLogAboutAdopt(MoneyLogInsertRequestVO request) {
        moneyLogRepository.addMoneyLogAboutAdopt(request);
    }

    public void addMoneyLogByCharge(MoneyLogInsertRequestVO request) {
        moneyLogRepository.addMoneyLogByCharge(request);
    }
}
