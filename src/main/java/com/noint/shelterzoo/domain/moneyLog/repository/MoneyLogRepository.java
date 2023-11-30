package com.noint.shelterzoo.domain.moneyLog.repository;

import com.noint.shelterzoo.domain.moneyLog.vo.req.MoneyLogInsertRequestVO;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyLogRepository {
    void moneyLogInsertForAdoptReservation(MoneyLogInsertRequestVO params);
}
