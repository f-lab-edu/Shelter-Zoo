package com.noint.shelterzoo.controller.infraTest;

import com.noint.shelterzoo.service.infraTest.InfraTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InfraTestController {
    private final InfraTestService service;

    @GetMapping("/")
    public void test(){
        service.test();
    }
}
