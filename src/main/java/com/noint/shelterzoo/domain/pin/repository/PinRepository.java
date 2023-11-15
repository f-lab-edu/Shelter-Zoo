package com.noint.shelterzoo.domain.pin.repository;

import com.noint.shelterzoo.domain.pin.vo.req.PinUpRequestVO;
import org.springframework.stereotype.Repository;

@Repository
public interface PinRepository {
    void pinUp(PinUpRequestVO params);

    void pinUpDel(PinUpRequestVO params);
}
