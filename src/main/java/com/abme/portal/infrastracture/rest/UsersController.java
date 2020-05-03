package com.abme.portal.infrastracture.rest;

import com.abme.portal.domain.post.Post;
import com.abme.portal.domain.post.PostDto;
import com.abme.portal.domain.user.User;
import com.abme.portal.exceptions.UserNotFoundException;
import com.abme.portal.domain.user.UserRepository;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    public Iterable<PostDto> getUsersPosts(@PathVariable("userUuid") UUID userUuid)
    {
        User author = userRepository.findOneByUuid(userUuid).orElseThrow(UserNotFoundException::new);
        return author.getPosts().stream().map(PostDto::fromPost).collect(Collectors.toList());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JSONObject handleUserNotFound(UserNotFoundException e) {
        return new JSONObject(Collections.singletonMap("error", e.getMessage()));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{userUuid}")
    public User getUserData(@PathVariable("userUuid") UUID userUuid)
    {
        return userRepository.findOneByUuid(userUuid).orElseThrow(UserNotFoundException::new);
    }
}