package com.noint.shelterzoo.domain.abandoned.vo.res;

import lombok.Data;

@Data
public class AbandonedDetailResponseVO {
    private Long seq;
    private String happenPlace;
    private String kind;
    private String kindDetail;
    private String color;
    private String birth;
    private String weight;
    private String noticeEnd;
    private String gender;
    private String neuter;
    private String specialMark;
    private String img;
    private String adoptProcess;
    private String shelterName;
    private String shelterTel;
    private String shelterAddr;
}
