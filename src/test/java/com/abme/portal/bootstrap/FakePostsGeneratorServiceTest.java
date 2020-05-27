package com.abme.portal.bootstrap;

import com.abme.portal.domain.post.AddPostDto;
import com.abme.portal.domain.post.PostDto;
import com.abme.portal.domain.post.PostFacade;
import com.abme.portal.domain.user.User;
import com.abme.portal.domain.user.UserRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FakePostsGeneratorServiceTest {

    public static final int POSTS_COUNT = 100;
    FakePostsGeneratorService fakePostsGeneratorService;
    public static final int USER_ID = 0;
    final User user = new User(USER_ID);

    @Mock
    PostFacade postFacade;

    @Mock
    UserRepository userRepository;

    @Captor
    ArgumentCaptor<AddPostDto> captor;

    @BeforeEach
    void setUp() {
        fakePostsGeneratorService = new FakePostsGeneratorService(userRepository, postFacade, Faker.instance());
    }

    private static final UUID POST_UUID = UUID.fromString("06bdb428-f71b-456b-b132-1f95903a8548");

    private static final PostDto postDto = new PostDto(
            POST_UUID,
            "URL",
            "description",
            null,
            Set.of("label1", "label2")
    );


    @Test
    void insertPosts() {
        //given
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(postFacade.addPostWithLabelsToUserWithEmail(any(), eq(user.getEmail())))
                .thenReturn(postDto);

        //when
        fakePostsGeneratorService.insertPosts(POSTS_COUNT);

        //then
        verify(userRepository, times(1)).findAll();
        verify(postFacade, times(POSTS_COUNT)).addPostWithLabelsToUserWithEmail(captor.capture(), any());

        assertThat(captor.getValue(), (
                allOf(
                        hasProperty("uuid", notNullValue()),
                        hasProperty("description", not(blankOrNullString())),
                        hasProperty("URL", not(blankOrNullString()))
                )));
    }
}