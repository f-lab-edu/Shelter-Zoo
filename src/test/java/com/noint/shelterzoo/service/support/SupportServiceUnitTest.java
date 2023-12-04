package com.noint.shelterzoo.service.support;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.noint.shelterzoo.domain.support.dto.res.SupportPetListResponseDTO;
import com.noint.shelterzoo.domain.support.repository.SupportRepository;
import com.noint.shelterzoo.domain.support.service.SupportService;
import com.noint.shelterzoo.domain.support.vo.res.SupportPetListResponseVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {SupportService.class})
public class SupportServiceUnitTest {
    @Autowired
    SupportService supportService;

    @MockBean
    SupportRepository supportRepository;

    @Test
    @DisplayName("후원이 필요한 동물 리스트")
    void getSupportPetList() {
        // given
        Integer pageNum = 1;
        Integer pageSize = 1;
        SupportPetListResponseVO hopeValue = new SupportPetListResponseVO();
        hopeValue.setSupportPetSeq(204L);
        hopeValue.setPetSeq(199L);
        hopeValue.setRequiredMoney(BigDecimal.valueOf(100000));
        hopeValue.setSupportedMoney(BigDecimal.valueOf(0));
        hopeValue.setDisease("골절");
        hopeValue.setCreatedAt("2023-12-04 16:49:08");
        hopeValue.setKind("개");
        hopeValue.setKindDetail("믹스견");
        hopeValue.setBirth("2023(년생)");
        hopeValue.setThumbnail("http://www.animal.go.kr/files/shelter/2023/07/202310161410103_s.jpg");
        List<SupportPetListResponseVO> hopeValueList = new ArrayList<>();
        hopeValueList.add(hopeValue);


        // when
        when(supportRepository.getSupportPetList(any())).thenReturn(hopeValueList);

        // then
        PageInfo<SupportPetListResponseDTO> supportPetList = supportService.getSupportPetList(new PageParam(pageNum, pageSize));
        assertEquals(pageNum, supportPetList.getPageNum());
    }
}
