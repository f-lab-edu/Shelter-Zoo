package com.noint.shelterzoo.domain.support.vo.req;

import com.github.pagehelper.PageParam;
import lombok.Data;

@Data
public class SupportPetListRequestVO {
    private Integer pageNum;
    private Integer PageSize;

    public static SupportPetListRequestVO create(PageParam request) {
        SupportPetListRequestVO vo = new SupportPetListRequestVO();
        vo.setPageNum(request.getPageNum());
        vo.setPageSize(request.getPageSize());

        return vo;
    }
}
