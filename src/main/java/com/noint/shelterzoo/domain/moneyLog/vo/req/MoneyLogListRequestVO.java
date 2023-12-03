package com.noint.shelterzoo.domain.moneyLog.vo.req;

import com.github.pagehelper.PageParam;
import lombok.Data;

@Data
public class MoneyLogListRequestVO {
    private Long userSeq;
    private Integer pageNum;
    private Integer pageSize;

    public static MoneyLogListRequestVO create(Long userSeq, PageParam request) {
        MoneyLogListRequestVO vo = new MoneyLogListRequestVO();
        vo.setUserSeq(userSeq);
        vo.setPageNum(request.getPageNum());
        vo.setPageSize(request.getPageSize());

        return vo;
    }
}
