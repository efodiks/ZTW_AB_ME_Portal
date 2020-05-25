package com.abme.portal.infrastracture.rest;

import com.abme.portal.bootstrap.DevBootstrap;
import com.abme.portal.domain.post.PostDto;
import com.abme.portal.domain.user.UserFacade;
import com.abme.portal.domain.user.UserStubDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.gcp.autoconfigure.vision.CloudVisionAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.in;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ActiveProfiles("dev")
@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = CloudVisionAutoConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsersControllerTest {

    private static final UUID VALID_USER_ID = UUID.randomUUID();

    private static final UUID INVALID_USER_ID = UUID.randomUUID();

    private static final UUID POST_ID_1 = UUID.randomUUID();

    private static final UUID POST_ID_2 = UUID.randomUUID();

    private static final String[] IDS_STRING_ARRAY = {POST_ID_1.toString(), POST_ID_2.toString()};

    public static final String API_USERS_USER_ID_POSTS = "/api/users/%s/posts";

    private final UserStubDto user = new UserStubDto(
            VALID_USER_ID,
            "",
            "",
            "",
            "",
            ""
    );

    private Set<PostDto> posts;

    @MockBean
    private UserFacade userFacade;

    @MockBean
    private DevBootstrap devBootstrap;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    private void mockOutput() {
        initializePosts();
        when(userFacade.getUsersPosts(VALID_USER_ID)).thenReturn(posts);
        when(userFacade.getUsersPosts(INVALID_USER_ID)).thenReturn(Set.of());
    }

    private void initializePosts() {
        posts = Stream.of(POST_ID_1, POST_ID_2)
                .map(id -> new PostDto(
                        id,
                        "",
                        "Post" + id,
                        user,
                        Set.of()
                ))
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
    void expect200OkAndResponseBodyWhenAuthorizedAndIdValid() throws Exception {
        mockMvc
                .perform(get(String.format(API_USERS_USER_ID_POSTS, VALID_USER_ID)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].uuid", in(IDS_STRING_ARRAY)))
                .andExpect(jsonPath("$[1].uuid", in(IDS_STRING_ARRAY)));
    }
}