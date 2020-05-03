package com.abme.portal.infrastracture.rest;

import com.abme.portal.domain.post.AddPostDto;
import com.abme.portal.domain.post.PostDto;
import com.abme.portal.domain.user.User;
import com.abme.portal.domain.user.UserDto;
import com.abme.portal.domain.user.UserRepository;
import com.abme.portal.exceptions.UserNotFoundException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/api/users")
@RestController
public class UsersController {

    private final UserRepository userRepository;

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{userUuid}/posts")
    public List<PostDto> getUsersPosts(@PathVariable("userUuid") UUID userUuid)
    {
        User author = userRepository.findOneByUuid(userUuid).orElseThrow(UserNotFoundException::new);
        return author.getPosts().stream().map(PostDto::fromPost).collect(Collectors.toList());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{userUuid}")
    public UserDto getUserData(@PathVariable("userUuid") UUID userUuid)
    {
        var user = userRepository.findOneByUuid(userUuid).orElseThrow(UserNotFoundException::new);
        return UserDto.from(user);
    }
}