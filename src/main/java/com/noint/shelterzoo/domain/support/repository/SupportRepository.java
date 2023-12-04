package com.noint.shelterzoo.domain.support.repository;

import com.noint.shelterzoo.domain.support.vo.req.AddSupportLogRequestVO;
import com.noint.shelterzoo.domain.support.vo.req.SupportPetListRequestVO;
import com.noint.shelterzoo.domain.support.vo.req.UpdateSupportPetMoneyRequestVO;
import com.noint.shelterzoo.domain.support.vo.res.SupportMoneysResponseVO;
import com.noint.shelterzoo.domain.support.vo.res.SupportPetListResponseVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportRepository {
    List<SupportPetListResponseVO> getSupportPetList(SupportPetListRequestVO params);

    SupportMoneysResponseVO getMoneysAboutSupport(Long supportPetSeq);

    void updateSupportedMoney(UpdateSupportPetMoneyRequestVO params);

    void addSupportLog(AddSupportLogRequestVO params);
}
