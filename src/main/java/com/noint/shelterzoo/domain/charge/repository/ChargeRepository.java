package com.noint.shelterzoo.domain.charge.repository;

import com.noint.shelterzoo.domain.charge.vo.req.ChargeLogRequestVO;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeRepository {
    int countChargeId(String chargeId);

    void addChargeLog(ChargeLogRequestVO params);
}
