package com.noint.shelterzoo.domain.pin.dto.res;

import com.github.pagehelper.PageInfo;
import com.noint.shelterzoo.domain.pin.vo.res.PinListResponseVO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PinListResponseDTO {
    private Long seq;
    private String thumbnail;
    private String kind;
    private String kindDetail;
    private String birth;
    private String gender;
    private String neuter;
    private String noticeEnd;
    private Boolean isReserve;

    public static PageInfo<PinListResponseDTO> create(PageInfo<PinListResponseVO> voPageInfo) {
        PageInfo<PinListResponseDTO> dtoPageInfo = new PageInfo<>();
        dtoPageInfo.setList(create(voPageInfo.getList()));
        dtoPageInfo.setIsFirstPage(voPageInfo.isIsFirstPage());
        dtoPageInfo.setIsLastPage(voPageInfo.isIsLastPage());
        dtoPageInfo.setPages(voPageInfo.getPages());
        dtoPageInfo.setHasNextPage(voPageInfo.isHasNextPage());
        dtoPageInfo.setEndRow(voPageInfo.getEndRow());
        dtoPageInfo.setHasPreviousPage(voPageInfo.isHasPreviousPage());
        dtoPageInfo.setNavigateFirstPage(voPageInfo.getNavigateFirstPage());
        dtoPageInfo.setNavigatepageNums(voPageInfo.getNavigatepageNums());
        dtoPageInfo.setNavigateLastPage(voPageInfo.getNavigateLastPage());
        dtoPageInfo.setNavigatePages(voPageInfo.getNavigatePages());
        dtoPageInfo.setNextPage(voPageInfo.getNextPage());
        dtoPageInfo.setPageNum(voPageInfo.getPageNum());
        dtoPageInfo.setPages(voPageInfo.getPages());
        dtoPageInfo.setPageSize(voPageInfo.getPageSize());
        dtoPageInfo.setPrePage(voPageInfo.getPrePage());
        dtoPageInfo.setSize(voPageInfo.getSize());
        dtoPageInfo.setStartRow(voPageInfo.getStartRow());
        dtoPageInfo.setTotal(voPageInfo.getTotal());

        return dtoPageInfo;
    }

    public static List<PinListResponseDTO> create(List<PinListResponseVO> voList) {
        List<PinListResponseDTO> dtoList = new ArrayList<>();
        for (var vo : voList) {
            dtoList.add(create(vo));
        }

        return dtoList;
    }

    public static PinListResponseDTO create(PinListResponseVO vo) {
        PinListResponseDTO dto = new PinListResponseDTO();
        dto.setSeq(vo.getSeq());
        dto.setThumbnail(vo.getThumbnail());
        dto.setKind(vo.getKind());
        dto.setKindDetail(vo.getKindDetail());
        dto.setBirth(vo.getBirth());
        dto.setGender(vo.getGender());
        dto.setNeuter(vo.getNeuter());
        dto.setNoticeEnd(vo.getNoticeEnd());
        dto.setNoticeEnd(vo.getNoticeEnd());
        dto.setIsReserve(vo.getIsReserve());

        return dto;
    }
}
