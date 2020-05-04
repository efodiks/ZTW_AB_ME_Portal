package com.abme.portal.infrastracture.rest;

import com.abme.portal.domain.post.PostDto;
import com.abme.portal.domain.user.FollowDto;
import com.abme.portal.domain.user.UserDto;
import com.abme.portal.domain.user.UserFacade;
import com.abme.portal.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UsersController {

    private final UserFacade userFacade;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{userUuid}/posts")
    public Set<PostDto> getUsersPosts(@PathVariable("userUuid") UUID userUuid) {
        log.info(String.format("Fetching only posts for user: %s", userUuid));
        return userFacade.getUsersPosts(userUuid);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{userUuid}")
    public UserDto getUserData(@PathVariable("userUuid") UUID userUuid) {
        log.info(String.format("Fetching data for user: %s", userUuid));
        return userFacade.getUserData(userUuid);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{userUuid}/addFollow")
    public ResponseEntity<Void> addFollow(@PathVariable("userUuid") UUID from, @RequestBody FollowDto followDto) {
        userFacade.addFollow(from, followDto.getTo());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{userUuid}/removeFollow")
    public ResponseEntity<Void> removeFollow(@PathVariable("userUuid") UUID from, @RequestBody FollowDto followDto) {
        userFacade.removeFollow(from, followDto.getTo());
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity
                .badRequest()
                .body(Collections.singletonMap("error", e.getMessage()));
    }
}