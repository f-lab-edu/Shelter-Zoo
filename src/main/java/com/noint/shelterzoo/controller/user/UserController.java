package com.noint.shelterzoo.controller.user;

import com.noint.shelterzoo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/email/check")
    public ResponseEntity<Boolean> emailDuplicateCheck(@RequestParam String email){
        return new ResponseEntity<>(userService.isExistEmail(email), HttpStatus.OK);
    }
}
