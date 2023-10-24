package com.noint.shelterzoo.controller.user;

import com.noint.shelterzoo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/email/check/{email}")
    public ResponseEntity<Boolean> emailDuplicateCheck(@PathVariable String email){
        return new ResponseEntity<>(userService.isExistEmail(email), HttpStatus.OK);
    }
}
