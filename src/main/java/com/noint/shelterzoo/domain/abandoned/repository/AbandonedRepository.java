package com.noint.shelterzoo.domain.abandoned.repository;

import com.noint.shelterzoo.domain.abandoned.vo.req.AbandonedListRequestVO;
import com.noint.shelterzoo.domain.abandoned.vo.req.AdoptProcessUpdateRequestVO;
import com.noint.shelterzoo.domain.abandoned.vo.req.AdoptReservationRequestVO;
import com.noint.shelterzoo.domain.abandoned.vo.req.AdoptUpdateRequestVO;
import com.noint.shelterzoo.domain.abandoned.vo.res.AbandonedDetailResponseVO;
import com.noint.shelterzoo.domain.abandoned.vo.res.AbandonedListResponseVO;
import com.noint.shelterzoo.domain.abandoned.vo.res.AdoptCancelDateDiffResponseVO;
import com.noint.shelterzoo.domain.abandoned.vo.res.ReservationCheckResponseVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbandonedRepository {
    List<AbandonedListResponseVO> getAbandonedList(AbandonedListRequestVO params);

    AbandonedDetailResponseVO getAbandonedPetDetail(Long petSeq);

    boolean isAdoptAble(Long petSeq);

    void reservationPet(AdoptReservationRequestVO params);

    void updateAdoptProcess(AdoptProcessUpdateRequestVO params);

    ReservationCheckResponseVO isReservationPet(AdoptUpdateRequestVO params);

    void updateAdoptPet(AdoptUpdateRequestVO params);

    AdoptCancelDateDiffResponseVO getDateDiffFromNow(AdoptUpdateRequestVO params);
}
