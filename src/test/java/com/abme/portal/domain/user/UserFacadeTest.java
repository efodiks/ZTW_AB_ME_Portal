package com.abme.portal.domain.user;

import com.abme.portal.domain.post.Post;
import com.abme.portal.domain.post.PostRepository;
import com.abme.portal.domain.role.Role;
import com.abme.portal.domain.role.RoleName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class UserFacadeTest {

    private final UUID VALID_USER_UUID1 = UUID.fromString("a56bd380-bfca-4cdb-8fa8-e54426884c59");
    private final UUID VALID_USER_UUID2 = UUID.fromString("9ac594ef-afc0-4a1e-a6ec-84ca52ae603d");
    private final UUID POST_1_UUID = UUID.fromString("06bdb428-f71b-456b-b132-1f95903a8548");
    private final UUID POST_2_UUID = UUID.fromString("94bfe009-5ea4-4ac0-be7b-6f51820aa004");

    private Set<Post> postSet;

    private User user1;
    private User user2;

    @Mock
    private UserRepository userRepository;

    @Mock
    PostRepository postRepository;

    private UserFacade userFacade;

    @BeforeEach
    void setUp() {
        userFacade = new UserFacade(userRepository, postRepository);
        postSet = Set.of(
                new Post(1L,
                        POST_1_UUID,
                        user1,
                        "url1",
                        "description1",
                        new HashSet<>()),
                new Post(2L,
                        POST_2_UUID,
                        user1,
                        "url2",
                        "description2",
                        new HashSet<>())
        );
        user1 = new User(
                1L,
                VALID_USER_UUID1,
                "email1",
                "",
                "firstName1",
                "lastName1",
                "username1",
                "photo1",
                new Role(RoleName.ROLE_USER),
                postSet,
                new HashSet<>(),
                new HashSet<>()
        );

        user2 = new User(
                2L,
                VALID_USER_UUID1,
                "email2",
                "",
                "firstName2",
                "lastName2",
                "username2",
                "photo2",
                new Role(RoleName.ROLE_USER),
                postSet,
                new HashSet<>(),
                new HashSet<>()
        );
    }

    @Test
    void getUserData() {
        //given
        when(userRepository.findOneByUuid(VALID_USER_UUID1)).thenReturn(Optional.of(user1));

        //when
        var userDto = userFacade.getUserData(VALID_USER_UUID1);

        //then
        assertEquals(VALID_USER_UUID1, userDto.getUuid());
        assertEquals(user1.getEmail(), userDto.getEmail());
        assertEquals(user1.getFirstName(), userDto.getFirstName());
        assertEquals(user1.getLastName(), userDto.getLastName());
        assertEquals(user1.getUsername(), userDto.getUsername());
        assertEquals(user1.getProfilePhotoUrl(), userDto.getProfilePhotoUrl());
    }

    @Test
    void getUsersPosts() {
        //given
        when(postRepository.findByAuthor_Uuid(VALID_USER_UUID1)).thenReturn(postSet);

        //when
        var postDtos = userFacade.getUsersPosts(VALID_USER_UUID1);

        //then
        assertThat(postDtos, everyItem(hasProperty("uuid", in(Set.of(POST_1_UUID, POST_2_UUID)))));
    }

    @Test
    void addFollow() {
        //given
        when(userRepository.findOneByUuid(VALID_USER_UUID1)).thenReturn(Optional.of(user1));
        when(userRepository.findOneByUuid(VALID_USER_UUID2)).thenReturn(Optional.of(user2));

        //when
        userFacade.addFollow(VALID_USER_UUID1, VALID_USER_UUID2);

        //then
        assertThat(user1.getFollowing(), contains(user2));
    }

    @Test
    void removeFollow() {
        //given
        user1.getFollowing().add(user2);
        when(userRepository.findOneByUuid(VALID_USER_UUID1)).thenReturn(Optional.of(user1));
        when(userRepository.findOneByUuid(VALID_USER_UUID2)).thenReturn(Optional.of(user2));

        //when
        userFacade.removeFollow(VALID_USER_UUID1, VALID_USER_UUID2);

        //then
        assertThat(user1.getFollowing(), is(empty()));
    }
}