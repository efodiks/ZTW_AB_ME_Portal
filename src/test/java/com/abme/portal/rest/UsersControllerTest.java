package com.abme.portal.rest;

import com.abme.portal.domain.Post;
import com.abme.portal.domain.User;
import com.abme.portal.repository.UserRepository;
import com.abme.portal.rest.exceptions.UserNotFoundException;
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

import java.util.List;
import java.util.Optional;
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

    private static final long VALID_USER_ID = 0L;

    private static final long INVALID_USER_ID = -1L;

    public static final String API_USERS_USER_ID_POSTS = "/api/users/%d/posts";

    private final User user = new User(VALID_USER_ID);

    private List<Post> posts;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    private void mockOutput() {
        initializePosts();
        user.setPosts(posts);
        when(userRepository.findById(VALID_USER_ID)).thenReturn(Optional.of(user));
        when(userRepository.findById(INVALID_USER_ID)).thenReturn(Optional.empty());
    }

    private void initializePosts() {
        posts = Stream.of(1L, 2L)
                .map(id -> {
                    final Post post = new Post();
                    post.setId(id);
                    post.setAuthor(user);
                    post.setDescription("Post" + id);
                    return post;
                })
                .collect(Collectors.toList());
    }

    @Test
    void expected403ForbiddenWhenNotAuthorized() throws Exception {
        mockMvc
                .perform(get(String.format(API_USERS_USER_ID_POSTS, VALID_USER_ID)))
                .andExpect(status().isForbidden());
    }

    @WithMockUser
    @Test
    void expect400BadRequestWhenUserIdIsNotValid() {
        Exception thrown = Assertions.assertThrows(
                NestedServletException.class,
                () -> mockMvc
                        .perform(get(String.format(API_USERS_USER_ID_POSTS, INVALID_USER_ID)))
        );
        Assertions.assertEquals(UserNotFoundException.class, thrown.getCause().getClass());
    }

    @WithMockUser
    @Test
    void expect200OkAndResponseBodyWhenAuthorizedAndIdValid() throws Exception {
        mockMvc
                .perform(get(String.format(API_USERS_USER_ID_POSTS, VALID_USER_ID)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", equalTo(posts.get(0).getId().intValue())))
                .andExpect(jsonPath("$[1].id", equalTo(posts.get(1).getId().intValue())));
    }
}