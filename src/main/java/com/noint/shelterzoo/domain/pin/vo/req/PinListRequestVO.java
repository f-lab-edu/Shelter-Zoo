package com.noint.shelterzoo.domain.pin.vo.req;

import com.github.pagehelper.PageParam;
import lombok.Data;

@Data
public class PinListRequestVO {
    private Long userSeq;
    private Integer pageNum;
    private Integer pageSize;

    public static PinListRequestVO create(Long userSeq, PageParam dto) {
        PinListRequestVO vo = new PinListRequestVO();
        vo.setUserSeq(userSeq);
        vo.setPageNum(dto.getPageNum());
        vo.setPageSize(dto.getPageSize());

        return vo;
    }
}
