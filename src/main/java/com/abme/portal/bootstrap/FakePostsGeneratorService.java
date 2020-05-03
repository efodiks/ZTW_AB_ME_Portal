package com.abme.portal.bootstrap;

import com.abme.portal.domain.Post;
import com.abme.portal.domain.user.User;
import com.abme.portal.repository.PostRepository;
import com.abme.portal.repository.UserRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class FakePostsGeneratorService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final Faker faker;
    private final Random random = new Random();

    private User[] users;

    public void insertPosts(int count) {
        users = StreamSupport.stream(userRepository.findAll().spliterator(), false).toArray(User[]::new);
        var posts = Stream
                .generate(() -> generatePostsFor(randomUser()))
                .limit(count)
                .collect(Collectors.toList());
        postRepository.saveAll(posts);
    }

    private User randomUser() {
        var randomIndex = random.nextInt(users.length);
        return users[randomIndex];
    }

    private Post generatePostsFor(User author) {
        return new Post()
                .setDescription(faker.rickAndMorty().quote())
                .setAuthor(author)
                .setURL(faker.internet().image());
    }
}
