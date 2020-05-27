package com.abme.portal.infrastracture.rest;

import com.abme.portal.domain.post.AddPostDto;
import com.abme.portal.domain.post.PostDto;
import com.abme.portal.domain.post.PostFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {

    private final PostFacade postFacade;

    @PostMapping("/posts/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostDto> addPost(@Valid @RequestBody AddPostDto addPostDto, Authentication authentication) {
        var postDto = postFacade.addPostWithLabelsToUserWithEmail(addPostDto, authentication.getName());
        log.info(String.format("Creating post with uuid %s for user with email %s", addPostDto.getUuid(), authentication.getName()));
        return ResponseEntity.status(HttpStatus.CREATED).body(postDto);
    }

    @GetMapping("/posts")
    @PreAuthorize("isAuthenticated()")
    public Iterable<PostDto> getPosts(Authentication authentication) {
        log.info(String.format("Getting posts feed for user with email %s", authentication.getName()));
        return postFacade.getPostsByAuthorEmailNot(authentication.getName());
    }

    @GetMapping("/posts/me")
    @PreAuthorize("isAuthenticated()")
    public Iterable<PostDto> getUsersPosts(Authentication authentication) {
        log.info(String.format("Getting user with email %s posts", authentication.getName()));
        return postFacade.getPostsByAuthorEmail(authentication.getName());
    }

    @GetMapping("/posts/suggested")
    @PreAuthorize("isAuthenticated()")
    public Iterable<PostDto> getSuggestedPosts(Authentication authentication) {
        log.info(String.format("Getting suggested posts for user with email %s", authentication.getName()));
        return postFacade.getSuggestedPostsForUserWithEmail(authentication.getName());
    }
}
