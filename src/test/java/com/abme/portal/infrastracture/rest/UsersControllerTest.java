package com.abme.portal.infrastracture.rest;

import com.abme.portal.bootstrap.DevBootstrap;
import com.abme.portal.domain.post.Post;
import com.abme.portal.domain.user.User;
import com.abme.portal.domain.user.UserRepository;
import com.abme.portal.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsersControllerTest {

    private static final UUID VALID_USER_ID = UUID.randomUUID();

    private static final UUID INVALID_USER_ID = UUID.randomUUID();

    public static final String API_USERS_USER_ID_POSTS = "/api/users/%s/posts";

    private final User user = new User(0L).setUuid(VALID_USER_ID);

    private Set<Post> posts;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private DevBootstrap devBootstrap;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    private void mockOutput() {
        initializePosts();
        user.setPosts(posts);
        when(userRepository.findOneByUuid(VALID_USER_ID)).thenReturn(Optional.of(user));
        when(userRepository.findOneByUuid(INVALID_USER_ID)).thenReturn(Optional.empty());
    }

    private void initializePosts() {
        posts = Stream.of(1L, 2L)
                .map(id -> {
                    final Post post = new Post();
                    post.setId(id);
                    post.setUuid(UUID.randomUUID());
                    post.setAuthor(user);
                    post.setDescription("Post" + id);
                    return post;
                })
                .collect(Collectors.toSet());
    }

    @Test
    void expected403ForbiddenWhenNotAuthorized() throws Exception {
        mockMvc
                .perform(get(String.format(API_USERS_USER_ID_POSTS, VALID_USER_ID)))
                .andExpect(status().isForbidden());
    }

    @WithMockUser
    @Test
    void expect400BadRequestWhenUserIdIsNotValid() throws Exception {
        mockMvc
                .perform(get(String.format(API_USERS_USER_ID_POSTS, INVALID_USER_ID)))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void expect200OkAndResponseBodyWhenAuthorizedAndIdValid() throws Exception {
        var postsIterator = posts.iterator();
        var firstPost = postsIterator.next();
        var secondPost = postsIterator.next();
        mockMvc
                .perform(get(String.format(API_USERS_USER_ID_POSTS, VALID_USER_ID)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].uuid", equalTo(firstPost.getUuid().toString())))
                .andExpect(jsonPath("$[1].uuid", equalTo(secondPost.getUuid().toString())));
    }
}