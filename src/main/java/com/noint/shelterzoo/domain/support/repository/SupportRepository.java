package com.noint.shelterzoo.domain.support.repository;

import com.noint.shelterzoo.domain.support.vo.req.SupportPetListRequestVO;
import com.noint.shelterzoo.domain.support.vo.res.SupportPetListResponseVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportRepository {
    List<SupportPetListResponseVO> getSupportPetList(SupportPetListRequestVO params);
}
