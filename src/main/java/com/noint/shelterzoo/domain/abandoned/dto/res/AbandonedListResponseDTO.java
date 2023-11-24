package com.noint.shelterzoo.domain.abandoned.dto.res;

import com.github.pagehelper.PageInfo;
import com.noint.shelterzoo.domain.abandoned.enums.GenderEnum;
import com.noint.shelterzoo.domain.abandoned.enums.NeuterEnum;
import com.noint.shelterzoo.domain.abandoned.vo.res.AbandonedListResponseVO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AbandonedListResponseDTO {
    private Long seq;
    private String thumbnail;
    private String kind;
    private String kindDetail;
    private String birth;
    private String gender;
    private String neuter;
    private String noticeEnd;
    private Boolean isPin;

    public static PageInfo<AbandonedListResponseDTO> create(PageInfo<AbandonedListResponseVO> voPageInfo) {
        PageInfo<AbandonedListResponseDTO> dtoCopyPageInfo = new PageInfo<>();
        dtoCopyPageInfo.setList(create(voPageInfo.getList()));
        dtoCopyPageInfo.setIsFirstPage(voPageInfo.isIsFirstPage());
        dtoCopyPageInfo.setIsLastPage(voPageInfo.isIsLastPage());
        dtoCopyPageInfo.setPages(voPageInfo.getPages());
        dtoCopyPageInfo.setHasNextPage(voPageInfo.isHasNextPage());
        dtoCopyPageInfo.setEndRow(voPageInfo.getEndRow());
        dtoCopyPageInfo.setHasPreviousPage(voPageInfo.isHasPreviousPage());
        dtoCopyPageInfo.setNavigateFirstPage(voPageInfo.getNavigateFirstPage());
        dtoCopyPageInfo.setNavigatepageNums(voPageInfo.getNavigatepageNums());
        dtoCopyPageInfo.setNavigateLastPage(voPageInfo.getNavigateLastPage());
        dtoCopyPageInfo.setNavigatePages(voPageInfo.getNavigatePages());
        dtoCopyPageInfo.setNextPage(voPageInfo.getNextPage());
        dtoCopyPageInfo.setPageNum(voPageInfo.getPageNum());
        dtoCopyPageInfo.setPages(voPageInfo.getPages());
        dtoCopyPageInfo.setPageSize(voPageInfo.getPageSize());
        dtoCopyPageInfo.setPrePage(voPageInfo.getPrePage());
        dtoCopyPageInfo.setSize(voPageInfo.getSize());
        dtoCopyPageInfo.setStartRow(voPageInfo.getStartRow());
        dtoCopyPageInfo.setTotal(voPageInfo.getTotal());

        return dtoCopyPageInfo;
    }

    public static List<AbandonedListResponseDTO> create(List<AbandonedListResponseVO> voList) {
        List<AbandonedListResponseDTO> dtoList = new ArrayList<>();
        for (var vo : voList) {
            dtoList.add(create(vo));
        }

        return dtoList;
    }

    public static AbandonedListResponseDTO create(AbandonedListResponseVO vo) {
        AbandonedListResponseDTO dto = new AbandonedListResponseDTO();
        dto.setBirth(vo.getBirth());
        dto.setSeq(vo.getSeq());
        dto.setThumbnail(vo.getThumbnail());
        dto.setKind(vo.getKind());
        dto.setKindDetail(vo.getKindDetail());
        dto.setGender(GenderEnum.findEnumByInitial(vo.getGender()).getFullText());
        dto.setNeuter(NeuterEnum.findEnumByInitial(vo.getNeuter()).getFullText());
        dto.setNoticeEnd(vo.getNoticeEnd());
        dto.setIsPin(vo.getIsPin());

        return dto;
    }
}
