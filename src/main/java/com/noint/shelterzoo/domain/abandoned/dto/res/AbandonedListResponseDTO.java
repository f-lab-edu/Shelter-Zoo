package com.noint.shelterzoo.domain.abandoned.dto.res;

import com.noint.shelterzoo.domain.abandoned.enums.GenderEnum;
import com.noint.shelterzoo.domain.abandoned.enums.NeuterEnum;
import com.noint.shelterzoo.domain.abandoned.vo.res.AbandonedListResponseVO;
import com.noint.shelterzoo.general.ListPageGeneral;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AbandonedListResponseDTO extends ListPageGeneral {
    private List<AbandonedRow> content;
    private boolean isNext;

    /**
     * 페이징은 무한 스크롤 방식으로 가정.
     * pageLength 만큼 content 생성.
     * voList.size()가 pageLength(20)보다 크다면 다음 페이지가 있다고 isNext로 알려줌.
     */
    public static AbandonedListResponseDTO create(List<AbandonedListResponseVO> voList) {
        AbandonedListResponseDTO dto = new AbandonedListResponseDTO();
        List<AbandonedRow> rowList = new ArrayList<>();
        int contentLimit = Math.min(voList.size(), pageLength);
        for (int i = 0; i < contentLimit; i++) {
            rowList.add(AbandonedRow.create(voList.get(i)));
        }
        dto.setContent(rowList);
        dto.setNext(voList.size() > pageLength);
        return dto;
    }

    @Data
    private static class AbandonedRow {
        private long seq;
        private String thumbnail;
        private String kind;
        private String kindDetail;
        private String birth;
        private String gender;
        private String neuter;
        private String noticeEnd;
        private boolean isPin;

        public static AbandonedRow create(AbandonedListResponseVO vo) {
            AbandonedRow dto = new AbandonedRow();
            dto.setBirth(vo.getBirth());
            dto.setSeq(vo.getSeq());
            dto.setThumbnail(vo.getThumbnail());
            dto.setKind(vo.getKind());
            dto.setKindDetail(vo.getKindDetail());
            dto.setGender(GenderEnum.findEnumByInitial(vo.getGender()).getFullText());
            dto.setNeuter(NeuterEnum.findEnumByInitial(vo.getNeuter()).getFullText());
            dto.setNoticeEnd(vo.getNoticeEnd());
            dto.setPin(vo.isPin());

            return dto;
        }
    }
}
