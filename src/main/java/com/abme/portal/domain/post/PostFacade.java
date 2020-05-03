package com.abme.portal.domain.post;


import com.abme.portal.domain.user.User;
import com.abme.portal.domain.user.UserRepository;
import com.abme.portal.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class PostFacade {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostDto addPostToUserWithEmail(AddPostDto addPostDto, String email) {
        var author = userRepository
                .findOneByEmailIgnoreCase(email)
                .orElseThrow(UserNotFoundException::new);
        Post post = Post.from(addPostDto);
        post.setAuthor(author);
        postRepository.save(post);
        return PostDto.fromPost(post);
    }

    public List<PostDto> getPostsByAuthorEmailNot(String email) {
        Optional<User> author = userRepository.findOneByEmailIgnoreCase(email);
        return postRepository
                .findByAuthorNot(author.orElseThrow(UserNotFoundException::new))
                .stream()
                .map(PostDto::fromPost)
                .collect(Collectors.toList());
    }

    public List<PostDto> getPostsByAuthorEmail(String email) {
        Optional<User> author = userRepository.findOneByEmailIgnoreCase(email);
        return postRepository
                .findByAuthor(author.orElseThrow(UserNotFoundException::new))
                .stream()
                .map(PostDto::fromPost)
                .collect(Collectors.toList());
    }
}
