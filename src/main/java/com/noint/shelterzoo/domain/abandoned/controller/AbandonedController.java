package com.noint.shelterzoo.domain.abandoned.controller;

import com.noint.shelterzoo.domain.abandoned.dto.req.AbandonedListRequestDTO;
import com.noint.shelterzoo.domain.abandoned.dto.res.AbandonedListResponseDTO;
import com.noint.shelterzoo.domain.abandoned.service.AbandonedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pet")
public class AbandonedController {
    private final AbandonedService abandonedService;
    private final HttpSession session;

    @GetMapping("/list")
    public ResponseEntity<AbandonedListResponseDTO> getAbandonedList(@ModelAttribute AbandonedListRequestDTO request){
        long userSeq = (long) session.getAttribute("seq");
        return new ResponseEntity<>(abandonedService.getAbandonedList(userSeq, request), HttpStatus.OK);
    }
}
