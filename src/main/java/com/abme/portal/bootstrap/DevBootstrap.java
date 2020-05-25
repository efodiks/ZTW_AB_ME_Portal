package com.abme.portal.bootstrap;


import com.abme.portal.domain.authentication.AuthenticationFacade;
import com.abme.portal.domain.label.LabelRepository;
import com.abme.portal.domain.post.Post;
import com.abme.portal.domain.post.PostRepository;
import com.abme.portal.domain.role.Role;
import com.abme.portal.domain.role.RoleName;
import com.abme.portal.domain.role.RoleRepository;
import com.abme.portal.domain.user.UserRegisterDto;
import com.abme.portal.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    public static final int POSTS_COUNT = 100;
    public static final int USERS_COUNT = 10;

    private final RoleRepository roleRepository;
    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LabelRepository labelRepository;
    private final FakeUserGeneratorService fakeUserGeneratorService;
    private final FakePostsGeneratorService fakePostsGeneratorService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (roleRepository.findAll().isEmpty()) {
            log.info("Bootstrapping roles");
            roleRepository.saveAll(List.of(
                    new Role(RoleName.ROLE_USER),
                    new Role(RoleName.ROLE_ADMIN)
            ));
        }

        if (!userRepository.findAll().iterator().hasNext()) {
            bootstrapUsers();
            log.info(String.format("Bootstrapping %d users", USERS_COUNT));
            fakeUserGeneratorService.insertFakeUsers(USERS_COUNT);
        }

        if (!postRepository.findAll().iterator().hasNext()) {
            log.info(String.format("Bootstrapping %d posts", POSTS_COUNT));
            fakePostsGeneratorService.insertPosts(POSTS_COUNT);
            bootStrapPosts();
        }
    }


    private void bootstrapUsers() {
        var user1 = new UserRegisterDto(
                UUID.randomUUID(),
                "user1@user1.com",
                "",
                "",
                "user1",
                "user1",
                "https://images.pexels.com/photos/941693/pexels-photo-941693.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"
        );

        var user2 = new UserRegisterDto(
                UUID.randomUUID(),
                "user2@user2.com",
                "",
                "",
                "user2",
                "user2",
                "https://images.pexels.com/photos/774909/pexels-photo-774909.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"
        );

        authenticationFacade.registerUser(user1);
        authenticationFacade.registerUser(user2);
    }

    private void bootStrapPosts() {
        var user1 = userRepository.findOneByEmailIgnoreCase("user1@user1.com");
        if (user1.isPresent()) {
            var post1User1 = new Post(
                    null,
                    UUID.randomUUID(),
                    user1.get(),
                    "https://www.4yourspot.com/wp-content/uploads/2019/10/daily-cat-care-routine.jpg",
                    "Kitty",
                    Set.of()
            );
            var post2User1 = new Post(
                    null,
                    UUID.randomUUID(),
                    user1.get(),
                    "https://images.pexels.com/photos/103123/pexels-photo-103123.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
                    "post nr 1",
                    Set.of()
            );

            postRepository.saveAll(List.of(post1User1, post2User1));
        }

        var user2 = userRepository.findOneByEmailIgnoreCase("user2@user2.com");
        if (user2.isPresent()) {
            var post1User2 = new Post(
                    null,
                    UUID.randomUUID(),
                    user2.get(),
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/7/71/Calico_tabby_cat_-_Savannah.jpg/1200px-Calico_tabby_cat_-_Savannah.jpg",
                    "Another kitty",
                    Set.of()
            );
        }
    }
}
