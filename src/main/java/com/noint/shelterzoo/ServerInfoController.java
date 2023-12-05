package com.noint.shelterzoo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server")
public class ServerInfoController {
    @Value("${server.port}")
    private String port;
    @Value("${version}")
    private String version;

    @GetMapping("/version")
    public ResponseEntity<String> version() {
        return new ResponseEntity<>(version, HttpStatus.OK);
    }

    @GetMapping("/port")
    public ResponseEntity<String> port() {
        return new ResponseEntity<>(port, HttpStatus.OK);
    }
}
