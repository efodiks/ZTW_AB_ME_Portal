package com.abme.portal.domain.user;

import com.abme.portal.domain.post.PostDto;
import com.abme.portal.domain.post.PostRepository;
import com.abme.portal.exceptions.UserNotFoundException;
import com.abme.portal.extensions.SetExtension;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public UserDto getUserData(UUID userUuid) {
        var user = userRepository.findOneByUuid(userUuid).orElseThrow(UserNotFoundException::new);
        return UserDto.fromUser(user);
    }

    public Set<PostDto> getUsersPosts(UUID uuid) {
        var posts = postRepository.findByAuthor_Uuid(uuid);
        return SetExtension.map(posts, PostDto::fromPost);
    }
}
