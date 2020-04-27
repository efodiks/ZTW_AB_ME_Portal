package com.abme.portal.rest;

import com.abme.portal.domain.Post;
import com.abme.portal.domain.User;
import com.abme.portal.repository.UserRepository;
import com.abme.portal.rest.exceptions.UserNotFoundException;
import com.abme.portal.security.AuthoritiesConstants;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RequestMapping("/api/users")
@RestController
public class UsersController {

    private final UserRepository userRepository;

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Secured(AuthoritiesConstants.USER)
    @GetMapping("/{userId}/posts")
    public Iterable<Post> getUsersPosts(@PathVariable("userId") long userId)
    {
        User author = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return author.getPosts();
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JSONObject handleUserNotFound(UserNotFoundException e) {
        return new JSONObject(Collections.singletonMap("error", e.getMessage()));
    }

    @Secured(AuthoritiesConstants.USER)
    @GetMapping("/{userId}")
    public User getUserData(@PathVariable("userId") long userId)
    {
        User author = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return author;
    }
}
