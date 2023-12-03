package com.noint.shelterzoo.domain.moneyLog.controller;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.noint.shelterzoo.domain.moneyLog.dto.res.MoneyLogDetailDTO;
import com.noint.shelterzoo.domain.moneyLog.dto.res.MoneyLogListResponseDTO;
import com.noint.shelterzoo.domain.moneyLog.service.MoneyLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class MoneyLogController {
    private final MoneyLogService moneyLogService;
    private final HttpSession session;

    @GetMapping("/money-logs")
    public ResponseEntity<PageInfo<MoneyLogListResponseDTO>> getMoneyLogList(@ModelAttribute PageParam request) {
        Long userSeq = (Long) session.getAttribute("userSeq");
        return new ResponseEntity<>(moneyLogService.getMoneyLogList(userSeq, request), HttpStatus.OK);
    }

    @GetMapping("/money-logs/{moneyLogSeq}")
    public ResponseEntity<MoneyLogDetailDTO> getMoneyLogDetail(@PathVariable Long moneyLogSeq) {
        Long userSeq = (Long) session.getAttribute("userSeq");
        return new ResponseEntity<>(moneyLogService.getMoneyLogDetail(userSeq, moneyLogSeq), HttpStatus.OK);
    }
}
