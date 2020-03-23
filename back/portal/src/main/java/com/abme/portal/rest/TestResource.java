package com.abme.portal.rest;

import com.abme.portal.security.AuthoritiesConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestResource {

    @GetMapping("/hello")
    @Secured(AuthoritiesConstants.USER)
    ResponseEntity<String> sayHelloToUser() {
        return ResponseEntity.ok("Hello");
    }
}
