package com.noint.shelterzoo.domain.pin.repository;

import com.noint.shelterzoo.domain.pin.vo.req.PinListRequestVO;
import com.noint.shelterzoo.domain.pin.vo.req.PinUpRequestVO;
import com.noint.shelterzoo.domain.pin.vo.res.PinListResponseVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PinRepository {
    void pinUp(PinUpRequestVO params);

    void pinUpDel(PinUpRequestVO params);

    List<PinListResponseVO> getPinupList(PinListRequestVO params);
}
