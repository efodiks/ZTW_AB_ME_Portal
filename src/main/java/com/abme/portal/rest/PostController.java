package com.abme.portal.rest;

import com.abme.portal.domain.Post;
import com.abme.portal.domain.user.User;
import com.abme.portal.dto.PostDTO;
import com.abme.portal.repository.PostRepository;
import com.abme.portal.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PostController
{
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostController(PostRepository postRepository, UserRepository userRepository)
    {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/posts/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Post> addPost(@Valid @RequestBody PostDTO postDTO)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> author = userRepository.findOneByEmailIgnoreCase(authentication.getName());
        Post post = new Post();
        post.setURL(postDTO.getURL());
        post.setDescription(postDTO.getDescription());
        //TODO this will blow 500 kaboom
        post.setAuthor(author.get());
        postRepository.save(post);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/posts")
    @PreAuthorize("isAuthenticated()")
    public Iterable<Post> getPosts()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> author = userRepository.findOneByEmailIgnoreCase(authentication.getName());
        //TODO this will blow 500 kaboom
        return postRepository.findByAuthorNot(author.get());
    }

    @GetMapping("/posts/me")
    @PreAuthorize("isAuthenticated()")
    public Iterable<Post> getUsersPosts()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> author = userRepository.findOneByEmailIgnoreCase(authentication.getName());
        //TODO this will blow 500 kaboom
        return postRepository.findByAuthor(author.get());
    }
}
