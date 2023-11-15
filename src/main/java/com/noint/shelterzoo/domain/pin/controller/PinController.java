package com.noint.shelterzoo.domain.pin.controller;

import com.noint.shelterzoo.domain.pin.service.PinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class PinController {
    private final PinService pinService;
    private final HttpSession session;


    @PostMapping("/pins/pets/{petSeq}")
    public ResponseEntity<Void> pinUp(@PathVariable long petSeq) {
        long userSeq = (long) session.getAttribute("userSeq");
        pinService.pinUp(userSeq, petSeq);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/pins/pets/{petSeq}")
    public ResponseEntity<Void> pinUpDel(@PathVariable long petSeq) {
        long userSeq = (long) session.getAttribute("userSeq");
        pinService.pinUpDel(userSeq, petSeq);
        return ResponseEntity.noContent().build();
    }
}
