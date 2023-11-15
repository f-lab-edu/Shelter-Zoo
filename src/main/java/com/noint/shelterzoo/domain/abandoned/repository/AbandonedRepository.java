package com.noint.shelterzoo.domain.abandoned.repository;

import com.noint.shelterzoo.domain.abandoned.vo.req.*;
import com.noint.shelterzoo.domain.abandoned.vo.res.AbandonedDetailResponseVO;
import com.noint.shelterzoo.domain.abandoned.vo.res.AbandonedListResponseVO;
import com.noint.shelterzoo.domain.abandoned.vo.res.AdoptCancelDateDiffResponseVO;
import com.noint.shelterzoo.domain.abandoned.vo.res.ReservationCheckResponseVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbandonedRepository {
    List<AbandonedListResponseVO> getAbandonedList(AbandonedListRequestVO params);

    AbandonedDetailResponseVO abandonedPetDetail(long petSeq);

    boolean isAdoptAble(long petSeq);

    void adoptPetForReservation(AdoptReservationRequestVO params);

    void adoptProcessUpdate(AdoptProcessUpdateRequestVO params);

    ReservationCheckResponseVO isReservationPet(AdoptUpdateRequestVO params);

    void adoptPetUpdate(AdoptUpdateRequestVO params);

    AdoptCancelDateDiffResponseVO adoptCancelAbleCheck(AdoptUpdateRequestVO params);

    void pinUp(PinUpRequestVO params);

    void pinUpDel(PinUpRequestVO params);
}
