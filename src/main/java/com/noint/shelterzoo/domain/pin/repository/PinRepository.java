package com.noint.shelterzoo.domain.pin.repository;

import com.noint.shelterzoo.domain.pin.vo.req.PinListRequestVO;
import com.noint.shelterzoo.domain.pin.vo.req.PinUpRequestVO;
import com.noint.shelterzoo.domain.pin.vo.res.PinListResponseVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PinRepository {
    void addPin(PinUpRequestVO params);

    void delPin(PinUpRequestVO params);

    List<PinListResponseVO> getPinList(PinListRequestVO params);
}
