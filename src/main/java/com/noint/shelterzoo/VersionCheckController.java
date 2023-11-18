package com.noint.shelterzoo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionCheckController {
    @Value("${server.port}")
    private Integer port;
    @Value("${version}")
    private String version;

    @GetMapping("/version")
    public ResponseEntity<String> version(){
        return new ResponseEntity<>("port : " + port + ", version : " + version, HttpStatus.OK);
    }
}
