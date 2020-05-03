package com.abme.portal.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestResource {

    @GetMapping("/hello")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<String> sayHelloToUser() {
        return ResponseEntity.ok("Hello");
    }
}
