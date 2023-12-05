package com.noint.shelterzoo.domain.support.controller;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.noint.shelterzoo.domain.support.dto.res.SupportPetListResponseDTO;
import com.noint.shelterzoo.domain.support.service.SupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SupportController {
    private final SupportService supportService;

    @GetMapping("/supports")
    public ResponseEntity<PageInfo<SupportPetListResponseDTO>> getSupportPetList(@ModelAttribute PageParam request) {
        return new ResponseEntity<>(supportService.getSupportPetList(request), HttpStatus.OK);
    }
}
