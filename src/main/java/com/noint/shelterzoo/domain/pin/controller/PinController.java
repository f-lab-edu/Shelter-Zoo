package com.noint.shelterzoo.domain.pin.controller;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
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
    public ResponseEntity<Void> addPin(@RequestAttribute("userSeq") Long userSeq, @PathVariable Long petSeq) {
        pinService.addPin(userSeq, petSeq);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/pins/pets/{petSeq}")
    public ResponseEntity<Void> delPin(@RequestAttribute("userSeq") Long userSeq, @PathVariable Long petSeq) {
        pinService.delPin(userSeq, petSeq);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pins/pets")
    public ResponseEntity<PageInfo<PinListResponseDTO>> getPinList(@RequestAttribute("userSeq") Long userSeq, @ModelAttribute PageParam request) {
        return new ResponseEntity<>(pinService.getPinList(userSeq, request), HttpStatus.OK);
    }
}
