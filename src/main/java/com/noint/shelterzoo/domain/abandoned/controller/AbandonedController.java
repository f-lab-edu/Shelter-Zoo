package com.noint.shelterzoo.domain.abandoned.controller;

import com.github.pagehelper.PageInfo;
import com.noint.shelterzoo.domain.abandoned.dto.req.AbandonedListRequestDTO;
import com.noint.shelterzoo.domain.abandoned.dto.req.AdoptReservationRequestDTO;
import com.noint.shelterzoo.domain.abandoned.dto.req.AdoptUpdateRequestDTO;
import com.noint.shelterzoo.domain.abandoned.dto.res.AbandonedDetailResponseDTO;
import com.noint.shelterzoo.domain.abandoned.dto.res.AbandonedListResponseDTO;
import com.noint.shelterzoo.domain.abandoned.service.AbandonedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class AbandonedController {
    private final AbandonedService abandonedService;
    private final HttpSession session;

    @GetMapping("/pets")
    public ResponseEntity<PageInfo<AbandonedListResponseDTO>> getAbandonedList(@ModelAttribute AbandonedListRequestDTO request) {
        Long userSeq = (Long) session.getAttribute("userSeq");
        return new ResponseEntity<>(abandonedService.getAbandonedList(userSeq, request), HttpStatus.OK);
    }

    @GetMapping("/pets/{petSeq}")
    public ResponseEntity<AbandonedDetailResponseDTO> getAbandonedPetDetail(@PathVariable Long petSeq) {
        return new ResponseEntity<>(abandonedService.getAbandonedPetDetail(petSeq), HttpStatus.OK);
    }

    @PostMapping("/adopt/pets/{petSeq}")
    public ResponseEntity<Void> reservationPet(@PathVariable Long petSeq, @RequestBody AdoptReservationRequestDTO request) {
        request.setPetSeq(petSeq);
        Long userSeq = (Long) session.getAttribute("userSeq");
        abandonedService.reservationPet(userSeq, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/adopt/pets/{petSeq}/{status}")
    public ResponseEntity<Void> updateAdoptPet(@PathVariable Long petSeq, @PathVariable String status) {
        Long userSeq = (Long) session.getAttribute("userSeq");
        AdoptUpdateRequestDTO request = new AdoptUpdateRequestDTO();
        request.setState(status);
        request.setPetSeq(petSeq);
        abandonedService.updateAdoptPet(userSeq, request);
        return ResponseEntity.noContent().build();
    }
}
