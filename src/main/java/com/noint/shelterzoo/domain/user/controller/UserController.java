package com.noint.shelterzoo.domain.user.controller;

import com.noint.shelterzoo.domain.user.dto.req.LoginRequestDTO;
import com.noint.shelterzoo.domain.user.dto.req.SignupRequestDTO;
import com.noint.shelterzoo.domain.user.dto.res.MyInfoResponseDTO;
import com.noint.shelterzoo.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final HttpSession session;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> addUser(@RequestBody SignupRequestDTO request) {
        userService.addUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/check/email/{email}")
    public ResponseEntity<Boolean> validateEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.isExistEmail(email), HttpStatus.OK);
    }

    @GetMapping("/check/nickname/{nickname}")
    public ResponseEntity<Boolean> validateNickname(@PathVariable String nickname) {
        return new ResponseEntity<>(userService.isExistNickname(nickname), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<MyInfoResponseDTO> login(@RequestBody LoginRequestDTO request) {
        MyInfoResponseDTO myInfo = userService.login(request);
        session.setAttribute("userSeq", myInfo.getSeq());
        session.setAttribute("userEmail", myInfo.getEmail());
        session.setAttribute("userNickname", myInfo.getNickname());
        session.setMaxInactiveInterval(60 * 30);

        return new ResponseEntity<>(myInfo, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<MyInfoResponseDTO> getUserInfo() {
        String email = (String) session.getAttribute("userEmail");
        return new ResponseEntity<>(userService.getUserInfo(email), HttpStatus.OK);
    }

    @PutMapping("/resign")
    public ResponseEntity<Void> resign(@RequestAttribute("userSeq") Long userSeq) {
        userService.resign(userSeq);
        session.invalidate();
        return ResponseEntity.noContent().build();
    }
}
