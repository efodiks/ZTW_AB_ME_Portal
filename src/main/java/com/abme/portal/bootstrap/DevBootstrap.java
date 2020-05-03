package com.abme.portal.bootstrap;


import com.abme.portal.domain.Post;
import com.abme.portal.domain.user.Role;
import com.abme.portal.domain.user.RoleName;
import com.abme.portal.dto.UserDTO;
import com.abme.portal.repository.AuthorityRepository;
import com.abme.portal.repository.PostRepository;
import com.abme.portal.repository.UserRepository;
import com.abme.portal.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    public static final int POSTS_COUNT = 100;
    public static final int USERS_COUNT = 10;

    private final AuthorityRepository authorityRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FakeUserGeneratorService fakeUserGeneratorService;
    private final FakePostsGeneratorService fakePostsGeneratorService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(authorityRepository.findAll().isEmpty()) {
            authorityRepository.saveAll(List.of(
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
        var user1 = new UserDTO();
        user1.setUsername("user1");
        user1.setEmail("user1@user1.com");
        user1.setPassword("user1");
        user1.setURL("https://images.pexels.com/photos/941693/pexels-photo-941693.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500");

        var user2 = new UserDTO();
        user2.setUsername("user2");
        user2.setEmail("user2@user2.com");
        user2.setPassword("user2");
        user2.setURL("https://images.pexels.com/photos/774909/pexels-photo-774909.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500");

        customUserDetailsService.registerUser(user1);
        customUserDetailsService.registerUser(user2);
    }

    private void bootStrapPosts() {
        var user1 = userRepository.findOneByEmailIgnoreCase("user1@user1.com");
        if (user1.isPresent()) {
            var post1User1 = new Post();
            post1User1.setDescription("Kitty");
            post1User1.setURL("https://www.4yourspot.com/wp-content/uploads/2019/10/daily-cat-care-routine.jpg");
            post1User1.setAuthor(user1.get());

            var post2User1 = new Post();
            post2User1.setDescription("post nr 1");
            post2User1.setURL("https://images.pexels.com/photos/103123/pexels-photo-103123.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500");
            post2User1.setAuthor(user1.get());

            postRepository.saveAll(List.of(post1User1, post2User1));
        }

        var user2 = userRepository.findOneByEmailIgnoreCase("user2@user2.com");
        if (user2.isPresent()) {
            var post1User2 = new Post();
            post1User2.setDescription("Another kitty");
            post1User2.setURL("https://upload.wikimedia.org/wikipedia/commons/thumb/7/71/Calico_tabby_cat_-_Savannah.jpg/1200px-Calico_tabby_cat_-_Savannah.jpg");
            post1User2.setAuthor(user2.get());
            postRepository.save(post1User2);
        }
    }
}
