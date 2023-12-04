package com.noint.shelterzoo.domain.support.dto.res;

import com.github.pagehelper.PageInfo;
import com.noint.shelterzoo.domain.support.vo.res.SupportPetListResponseVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class SupportPetListResponseDTO {
    private Long supportPetSeq;
    private Long petSeq;
    private BigDecimal requiredMoney;
    private BigDecimal remainingMoney;
    private String disease;
    private String createdAt;
    private String kind;
    private String kindDetail;
    private String birth;
    private String thumbnail;

    public static PageInfo<SupportPetListResponseDTO> create(PageInfo<SupportPetListResponseVO> voPageInfo) {
        PageInfo<SupportPetListResponseDTO> dtoPageInfo = new PageInfo<>();
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

    public static List<SupportPetListResponseDTO> create(List<SupportPetListResponseVO> voList) {
        List<SupportPetListResponseDTO> dtoList = new ArrayList<>();
        for (var vo : voList) {
            dtoList.add(create(vo));
        }

        return dtoList;
    }

    public static SupportPetListResponseDTO create(SupportPetListResponseVO vo) {
        SupportPetListResponseDTO dto = new SupportPetListResponseDTO();
        dto.setSupportPetSeq(vo.getSupportPetSeq());
        dto.setPetSeq(vo.getPetSeq());
        dto.setRequiredMoney(vo.getRequiredMoney());
        dto.setRemainingMoney(vo.getRequiredMoney().subtract(vo.getSupportedMoney()));
        dto.setDisease(vo.getDisease());
        dto.setCreatedAt(vo.getCreatedAt());
        dto.setKind(vo.getKind());
        dto.setKindDetail(vo.getKindDetail());
        dto.setBirth(vo.getBirth());
        dto.setThumbnail(vo.getThumbnail());

        return dto;
    }
}
