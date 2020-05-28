package com.abme.portal.bootstrap;

import com.abme.portal.domain.post.AddPostDto;
import com.abme.portal.domain.post.PostFacade;
import com.abme.portal.domain.user.User;
import com.abme.portal.domain.user.UserRepository;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
public class FakePostsGeneratorService {


    public final String activeProfile;
    private final UserRepository userRepository;
    private final PostFacade postFacade;
    private final Faker faker;
    private final ImageUrlProvider imageUrlProvider;
    private final Random random = new Random();

    public FakePostsGeneratorService(@Value("${spring.profiles.active}") String activeProfile, UserRepository userRepository, PostFacade postFacade, Faker faker, ImageUrlProvider imageUrlProvider) {
        this.activeProfile = activeProfile;
        this.userRepository = userRepository;
        this.postFacade = postFacade;
        this.faker = faker;
        this.imageUrlProvider = imageUrlProvider;
    }

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
                imageUrlProvider.randomUrl(),
                faker.rickAndMorty().quote()
        );
        log.info("Adding random post with id {}", postDto.getUuid());
        if (activeProfile.equals("dev")) {
            postFacade.addPostToUserWithEmail(postDto, author.getEmail());
        } else {
            log.info("with labels");
            postFacade.addPostWithLabelsToUserWithEmail(postDto, author.getEmail());
        }
    }
}
