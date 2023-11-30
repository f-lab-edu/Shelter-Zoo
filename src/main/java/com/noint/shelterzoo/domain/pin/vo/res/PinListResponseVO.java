package com.noint.shelterzoo.domain.pin.vo.res;

import lombok.Data;

@Data
public class PinListResponseVO {
    private Long seq;
    private String thumbnail;
    private String kind;
    private String kindDetail;
    private String birth;
    private String gender;
    private String neuter;
    private String noticeEnd;
    private Boolean isReserve;
}
