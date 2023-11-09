package com.noint.shelterzoo.domain.abandoned.repository;

import com.noint.shelterzoo.domain.abandoned.vo.req.AbandonedListRequestVO;
import com.noint.shelterzoo.domain.abandoned.vo.res.AbandonedDetailResponseVO;
import com.noint.shelterzoo.domain.abandoned.vo.res.AbandonedListResponseVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbandonedRepository {
    List<AbandonedListResponseVO> getAbandonedList(AbandonedListRequestVO params);

    AbandonedDetailResponseVO abandonedPetDetail(long petSeq);
}
