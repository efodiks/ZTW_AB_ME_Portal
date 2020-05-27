package com.abme.portal.bootstrap;

import com.abme.portal.domain.post.AddPostDto;
import com.abme.portal.domain.post.PostFacade;
import com.abme.portal.domain.user.User;
import com.abme.portal.domain.user.UserRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FakePostsGeneratorService {
    private final UserRepository userRepository;
    private final PostFacade postFacade;
    private final Faker faker;
    private final Random random = new Random();

    private List<User> users;

    public void insertPosts(int count) {
        users = userRepository.findAll();

        for (int i = 0; i < count; i++) {
            addRandomPostTo(randomUser());
        }
    }

    private User randomUser() {
        var randomIndex = random.nextInt(users.size());
        return users.get(randomIndex);
    }

    private void addRandomPostTo(User author) {
        var postDto = new AddPostDto(
                UUID.randomUUID(),
                faker.internet().image(),
                faker.rickAndMorty().quote()
        );
        postFacade.addPostWithLabelsToUserWithEmail(postDto, author.getEmail());
    }
}
