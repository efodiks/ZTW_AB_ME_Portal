package com.abme.portal.infrastracture.rest;

import com.abme.portal.domain.post.AddPostDto;
import com.abme.portal.domain.post.PostDto;
import com.abme.portal.domain.post.PostFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {
    private final PostFacade postFacade;

    @PostMapping("/posts/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostDto> addPost(@Valid @RequestBody AddPostDto addPostDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var postDto = postFacade.addPostToUserWithEmail(addPostDto, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(postDto);
    }

    @GetMapping("/posts")
    @PreAuthorize("isAuthenticated()")
    public Iterable<PostDto> getPosts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return postFacade.getPostsByAuthorEmailNot(authentication.getName());
    }

    @GetMapping("/posts/me")
    @PreAuthorize("isAuthenticated()")
    public Iterable<PostDto> getUsersPosts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return postFacade.getPostsByAuthorEmail(authentication.getName());
    }
}
