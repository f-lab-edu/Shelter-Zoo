package com.noint.shelterzoo.domain.moneyLog.dto.res;

import com.github.pagehelper.PageInfo;
import com.noint.shelterzoo.domain.moneyLog.vo.res.MoneyLogListResponseVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class MoneyLogListResponseDTO {
    private Long moneyLogSeq;
    private Long userSeq;
    private String moneyType;
    private BigDecimal money;
    private BigDecimal totalMoney;
    private String type;
    private Long adoptSeq;
    private Long supportLogSeq;
    private Long chargeLogSeq;
    private String createAt;

    public static PageInfo<MoneyLogListResponseDTO> create(PageInfo<MoneyLogListResponseVO> voPageInfo) {
        PageInfo<MoneyLogListResponseDTO> dtoPageInfo = new PageInfo<>();
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

    public static List<MoneyLogListResponseDTO> create(List<MoneyLogListResponseVO> voList) {
        List<MoneyLogListResponseDTO> dtoList = new ArrayList<>();
        for (var vo : voList) {
            dtoList.add(create(vo));
        }

        return dtoList;
    }

    public static MoneyLogListResponseDTO create(MoneyLogListResponseVO vo) {
        MoneyLogListResponseDTO dto = new MoneyLogListResponseDTO();
        dto.setMoneyLogSeq(vo.getMoneyLogSeq());
        dto.setUserSeq(vo.getUserSeq());
        dto.setMoneyType(vo.getMoneyType());
        dto.setMoney(vo.getMoney());
        dto.setTotalMoney(vo.getTotalMoney());
        dto.setAdoptSeq(vo.getAdoptSeq());
        dto.setSupportLogSeq(vo.getSupportLogSeq());
        dto.setChargeLogSeq(vo.getChargeLogSeq());
        dto.setCreateAt(vo.getCreateAt());

        return dto;
    }
}
