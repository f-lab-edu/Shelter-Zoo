package com.noint.shelterzoo.domain.moneyLog.repository;

import com.noint.shelterzoo.domain.moneyLog.vo.req.MoneyLogAddRequestVO;
import com.noint.shelterzoo.domain.moneyLog.vo.req.MoneyLogDetailRequestVO;
import com.noint.shelterzoo.domain.moneyLog.vo.req.MoneyLogListRequestVO;
import com.noint.shelterzoo.domain.moneyLog.vo.res.MoneyLogDetailWithAdoptResVO;
import com.noint.shelterzoo.domain.moneyLog.vo.res.MoneyLogDetailWithChargeResVO;
import com.noint.shelterzoo.domain.moneyLog.vo.res.MoneyLogDetailWithSupportResVO;
import com.noint.shelterzoo.domain.moneyLog.vo.res.MoneyLogListResponseVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoneyLogRepository {
    void addMoneyLogAboutAdopt(MoneyLogAddRequestVO params);

    void addMoneyLogByCharge(MoneyLogAddRequestVO params);

    List<MoneyLogListResponseVO> getMoneyLogList(MoneyLogListRequestVO params);

    String getMoneyLogType(MoneyLogDetailRequestVO params);

    MoneyLogDetailWithAdoptResVO getMoneyLogDetailWithAdopt(MoneyLogDetailRequestVO params);

    MoneyLogDetailWithChargeResVO getMoneyLogDetailWithCharge(MoneyLogDetailRequestVO params);

    MoneyLogDetailWithSupportResVO getMoneyLogDetailWithSupport(MoneyLogDetailRequestVO params);
}
