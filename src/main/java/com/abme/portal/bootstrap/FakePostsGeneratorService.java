package com.abme.portal.bootstrap;

import com.abme.portal.domain.post.Post;
import com.abme.portal.domain.user.User;
import com.abme.portal.domain.post.PostRepository;
import com.abme.portal.domain.user.UserRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FakePostsGeneratorService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final Faker faker;
    private final Random random = new Random();

    private List<User> users;

    public void insertPosts(int count) {
        users = userRepository.findAll();
        var posts = Stream
                .generate(() -> generatePostsFor(randomUser()))
                .limit(count)
                .collect(Collectors.toList());
        postRepository.saveAll(posts);
    }

    private User randomUser() {
        var randomIndex = random.nextInt(users.size());
        return users.get(randomIndex);
    }

    private Post generatePostsFor(User author) {
        return new Post()
                .setUuid(UUID.randomUUID())
                .setDescription(faker.rickAndMorty().quote())
                .setAuthor(author)
                .setURL(faker.internet().image());
    }
}
