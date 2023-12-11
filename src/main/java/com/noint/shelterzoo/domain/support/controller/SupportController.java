package com.noint.shelterzoo.domain.support.controller;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.noint.shelterzoo.domain.support.dto.req.DonateRequestDTO;
import com.noint.shelterzoo.domain.support.dto.res.SupportPetListResponseDTO;
import com.noint.shelterzoo.domain.support.service.SupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class SupportController {
    private final SupportService supportService;
    private final HttpSession session;

    @GetMapping("/supports")
    public ResponseEntity<PageInfo<SupportPetListResponseDTO>> getSupportPetList(@ModelAttribute PageParam request) {
        return new ResponseEntity<>(supportService.getSupportPetList(request), HttpStatus.OK);
    }

    @PostMapping("/supports/pets/{supportPetSeq}")
    public ResponseEntity<Void> doDonate(@PathVariable Long supportPetSeq, @RequestBody DonateRequestDTO request) {
        Long userSeq = (Long) session.getAttribute("userSeq");
        request.setSupportPetSeq(supportPetSeq);
        supportService.doDonate(userSeq, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
