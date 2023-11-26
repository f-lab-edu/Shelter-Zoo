package com.noint.shelterzoo.domain.charge.controller;

import com.noint.shelterzoo.domain.charge.dto.req.ChargeMoneyRequestDTO;
import com.noint.shelterzoo.domain.charge.service.ChargeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class ChargeController {
    private final ChargeService chargeService;
    private final HttpSession session;

    @PostMapping("/charge")
    public ResponseEntity<Void> chargeMoney(@RequestBody ChargeMoneyRequestDTO request) {
        Long userSeq = (Long) session.getAttribute("userSeq");
        chargeService.chargeMoney(userSeq, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
