package com.abme.portal.bootstrap;

import com.abme.portal.domain.label.Label;
import com.abme.portal.domain.label.LabelRepository;
import com.abme.portal.domain.post.Post;
import com.abme.portal.domain.user.User;
import com.abme.portal.domain.post.PostRepository;
import com.abme.portal.domain.user.UserRepository;
import com.abme.portal.extensions.ListExtension;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FakePostsGeneratorService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LabelRepository labelRepository;
    private final Faker faker;
    private final Random random = new Random();

    private List<User> users;
    private List<Label> labels;

    private final int RANDOM_LABELS_LOWER_BOUND = 1;
    private final int RANDOM_LABELS_UPPER_BOUND_EXCLUSIVE = 5;

    public void insertPosts(int count) {
        users = userRepository.findAll();
        labels = labelRepository.findAll();

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
        return new Post(
                null,
                UUID.randomUUID(),
                author,
                faker.internet().image(),
                faker.rickAndMorty().quote(),
                randomLabels()
        );
    }

    private Set<Label> randomLabels() {
        var randomLabels = ListExtension
                .randomListElements(labels, RANDOM_LABELS_LOWER_BOUND, RANDOM_LABELS_UPPER_BOUND_EXCLUSIVE, random);
        return new HashSet<>(randomLabels);
    }
}
