package com.noint.shelterzoo.domain.moneyLog.service;

import com.noint.shelterzoo.domain.moneyLog.enums.MoneyTypeEnum;
import com.noint.shelterzoo.domain.moneyLog.repository.MoneyLogRepository;
import com.noint.shelterzoo.domain.moneyLog.vo.req.MoneyLogInsertRequestVO;
import com.noint.shelterzoo.domain.user.enums.MoneyUpdatePurposeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class MoneyLogService {
    private final MoneyLogRepository moneyLogRepository;

    public void insertLogAboutAdopt(MoneyLogInsertRequestVO request) {
        moneyLogRepository.insertLogAboutAdopt(request);
    }

    public void insertLogByCharge(MoneyLogInsertRequestVO request) {
        moneyLogRepository.insertLogByCharge(request);
    }
}
