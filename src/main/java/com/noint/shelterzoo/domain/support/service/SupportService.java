package com.noint.shelterzoo.domain.support.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.noint.shelterzoo.domain.support.dto.res.SupportPetListResponseDTO;
import com.noint.shelterzoo.domain.support.repository.SupportRepository;
import com.noint.shelterzoo.domain.support.vo.req.SupportPetListRequestVO;
import com.noint.shelterzoo.domain.support.vo.res.SupportPetListResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupportService {
    private final SupportRepository supportRepository;

    public PageInfo<SupportPetListResponseDTO> getSupportPetList(PageParam request) {
        PageInfo<SupportPetListResponseVO> pageInfo = PageHelper.startPage(request.getPageNum(), request.getPageSize())
                .doSelectPageInfo(() -> supportRepository.getSupportPetList(SupportPetListRequestVO.create(request)));
        return SupportPetListResponseDTO.create(pageInfo);
    }
}
