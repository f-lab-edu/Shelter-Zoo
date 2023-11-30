package com.noint.shelterzoo.domain.pin.controller;

import com.github.pagehelper.PageInfo;
import com.noint.shelterzoo.domain.pin.dto.req.PinListRequestDTO;
import com.noint.shelterzoo.domain.pin.dto.res.PinListResponseDTO;
import com.noint.shelterzoo.domain.pin.service.PinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class PinController {
    private final PinService pinService;
    private final HttpSession session;


    @PostMapping("/pins/pets/{petSeq}")
    public ResponseEntity<Void> pinUp(@PathVariable Long petSeq) {
        Long userSeq = (Long) session.getAttribute("userSeq");
        pinService.pinUp(userSeq, petSeq);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/pins/pets/{petSeq}")
    public ResponseEntity<Void> pinUpDel(@PathVariable Long petSeq) {
        Long userSeq = (Long) session.getAttribute("userSeq");
        pinService.pinUpDel(userSeq, petSeq);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pins/pets")
    public ResponseEntity<PageInfo<PinListResponseDTO>> getPinupList(@ModelAttribute PinListRequestDTO request) {
        Long userSeq = (Long) session.getAttribute("userSeq");
        return new ResponseEntity<>(pinService.getPinupList(userSeq, request), HttpStatus.OK);
    }
}
