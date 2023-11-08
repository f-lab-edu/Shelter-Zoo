package com.noint.shelterzoo.domain.abandoned.dto.res;

import com.noint.shelterzoo.domain.abandoned.enums.GenderEnum;
import com.noint.shelterzoo.domain.abandoned.enums.NeuterEnum;
import com.noint.shelterzoo.domain.abandoned.vo.res.AbandonedDetailResponseVO;
import lombok.Data;

@Data
public class AbandonedDetailResponseDTO {
    private long seq;
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

    public static AbandonedDetailResponseDTO create(AbandonedDetailResponseVO vo){
        AbandonedDetailResponseDTO dto = new AbandonedDetailResponseDTO();
        dto.setSeq(vo.getSeq());
        dto.setHappenPlace(vo.getHappenPlace());
        dto.setKind(vo.getKind());
        dto.setKindDetail(vo.getKindDetail());
        dto.setColor(vo.getColor());
        dto.setBirth(vo.getBirth());
        dto.setWeight(vo.getWeight());
        dto.setNoticeEnd(vo.getNoticeEnd());
        dto.setGender(GenderEnum.findEnumByInitial(vo.getGender()).getFullText());
        dto.setNeuter(NeuterEnum.findEnumByInitial(vo.getNeuter()).getFullText());
        dto.setSpecialMark(vo.getSpecialMark());
        dto.setImg(vo.getImg());
        dto.setAdoptProcess(vo.getAdoptProcess());
        dto.setShelterName(vo.getShelterName());
        dto.setShelterTel(vo.getShelterTel());
        dto.setShelterAddr(vo.getShelterAddr());

        return dto;
    }
}
