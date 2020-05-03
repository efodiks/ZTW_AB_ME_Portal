package com.abme.portal.bootstrap;

import com.abme.portal.domain.post.Post;
import com.abme.portal.domain.user.User;
import com.abme.portal.domain.post.PostRepository;
import com.abme.portal.domain.user.UserRepository;
import com.github.javafaker.Faker;
import org.hamcrest.core.Every;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FakePostsGeneratorServiceTest {

    public static final int POSTS_COUNT = 100;
    FakePostsGeneratorService fakePostsGeneratorService;
    public static final int USER_ID = 0;
    final User user = new User(USER_ID);

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @Captor
    ArgumentCaptor<Iterable<Post>> captor;

    @BeforeEach
    void setUp() {
        fakePostsGeneratorService = new FakePostsGeneratorService(postRepository, userRepository, Faker.instance());
    }


    @Test
    void insertPosts() {
        //given
        when(userRepository.findAll()).thenReturn(List.of(user));

        //when
        fakePostsGeneratorService.insertPosts(POSTS_COUNT);

        //then
        verify(userRepository, times(1)).findAll();
        verify(postRepository, times(1)).saveAll(captor.capture());

        assertEquals(POSTS_COUNT, StreamSupport.stream(captor.getValue().spliterator(), false).count());
        assertThat(captor.getValue(), Every.everyItem(
                allOf(
                        hasProperty("uuid", notNullValue()),
                        hasProperty("author", equalTo(user)),
                        hasProperty("description", not(blankOrNullString())),
                        hasProperty("URL", not(blankOrNullString())),
                        hasProperty("id", nullValue()))));
    }
}