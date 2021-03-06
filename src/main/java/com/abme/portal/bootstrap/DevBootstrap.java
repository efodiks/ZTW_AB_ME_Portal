package com.abme.portal.bootstrap;


import com.abme.portal.domain.authentication.AuthenticationFacade;
import com.abme.portal.domain.label.LabelRepository;
import com.abme.portal.domain.post.AddPostDto;
import com.abme.portal.domain.post.Post;
import com.abme.portal.domain.post.PostFacade;
import com.abme.portal.domain.post.PostRepository;
import com.abme.portal.domain.role.Role;
import com.abme.portal.domain.role.RoleName;
import com.abme.portal.domain.role.RoleRepository;
import com.abme.portal.domain.user.UserRegisterDto;
import com.abme.portal.domain.user.UserRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ConditionalOnProperty(value = "data.bootstrap.enabled", matchIfMissing = true)
@Slf4j
@Component
@RequiredArgsConstructor
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    public static final int POSTS_COUNT = 100;
    public static final int USERS_COUNT = 10;

    @Value("${spring.profiles.active}")
    String activeProfile;

    private final RoleRepository roleRepository;
    private final AuthenticationFacade authenticationFacade;
    private final PostFacade postFacade;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FakeUserGeneratorService fakeUserGeneratorService;
    private final FakePostsGeneratorService fakePostsGeneratorService;
    private final ImageUrlProvider imageUrlProvider;

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent contextRefreshedEvent) {
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
            var post1User1 = new AddPostDto(
                    UUID.randomUUID(),
                    "https://www.4yourspot.com/wp-content/uploads/2019/10/daily-cat-care-routine.jpg",
                    "Kitty"
            );
            var post2User1 = new AddPostDto(
                    UUID.randomUUID(),
                    imageUrlProvider.randomUrl(),
                    "post nr 1"
            );
            if(activeProfile.equals("prod")){
                postFacade.addPostWithLabelsToUserWithEmail(post1User1, user1.get().getEmail());
                postFacade.addPostWithLabelsToUserWithEmail(post2User1, user1.get().getEmail());
            }
            else {
                postFacade.addPostToUserWithEmail(post1User1, user1.get().getEmail());
                postFacade.addPostToUserWithEmail(post2User1, user1.get().getEmail());
            }
        }

        var user2 = userRepository.findOneByEmailIgnoreCase("user2@user2.com");
        if (user2.isPresent()) {
            var post1User2 = new AddPostDto(
                    UUID.randomUUID(),
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/7/71/Calico_tabby_cat_-_Savannah.jpg/1200px-Calico_tabby_cat_-_Savannah.jpg",
                    "Another kitty"
            );
            if (activeProfile.equals("prod")) {
                postFacade.addPostWithLabelsToUserWithEmail(post1User2, user2.get().getEmail());
            } else {
                postFacade.addPostToUserWithEmail(post1User2, user2.get().getEmail());
            }
        }
    }
}
