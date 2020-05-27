package com.abme.portal.domain.post;

import com.abme.portal.domain.label.Label;
import com.abme.portal.domain.label.LabelService;
import com.abme.portal.domain.role.Role;
import com.abme.portal.domain.role.RoleName;
import com.abme.portal.domain.user.User;
import com.abme.portal.domain.user.UserRepository;
import com.abme.portal.extensions.SetExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostFacadeTest {

    public static final String POST_1_DESCRIPTION = "Post 1 description";
    public static final String POST_2_DESCRIPTION = "Post 2 description";
    private static final UUID POST_1_UUID = UUID.fromString("06bdb428-f71b-456b-b132-1f95903a8548");
    private static final UUID POST_2_UUID = UUID.fromString("94bfe009-5ea4-4ac0-be7b-6f51820aa004");
    private static final UUID POST_3_UUID = UUID.fromString("1af6e612-6e44-40be-ac0e-080d301de8fc");
    private static final UUID POST_4_UUID = UUID.fromString("7300cae7-bd57-43f2-9fe6-12cce50eb6ee");
    private static final UUID USER_UUID = UUID.fromString("a56bd380-bfca-4cdb-8fa8-e54426884c59");
    private static final UUID USER_NOT_UUID = UUID.fromString("6aa5d50d-830a-4500-9d76-3cb0bfb6255d");
    private static final String POST_1_URL = "post1_url";
    private static final String POST_2_URL = "post2_url";
    public static final String USER_EMAIL = "user@dom.com";

    public static final AddPostDto ADD_POST_DTO = new AddPostDto(
            POST_1_UUID,
            POST_1_URL,
            POST_1_DESCRIPTION
    );

    public static final Set<Label> labels = Set.of(
            new Label(1L, "label1"),
            new Label(2L, "label2")
    );

    public static final Optional<User> USER = Optional.of(
            new User(
                    1L,
                    USER_UUID,
                    USER_EMAIL,
                    "password_hash",
                    "firstName",
                    "lastName",
                    "username",
                    "ppurl",
                    new Role(RoleName.ROLE_USER),
                    new HashSet<>(),
                    Set.of(),
                    Set.of()
            )
    );

    public static final String USER_NOT_EMAIL = "user_not_email";
    public static final Optional<User> USER_NOT = Optional.of(
            new User(
                    2L,
                    USER_NOT_UUID,
                    USER_NOT_EMAIL,
                    "password_hash",
                    "firstName",
                    "lastName",
                    "username",
                    "ppurl",
                    new Role(RoleName.ROLE_USER),
                    new HashSet<Post>(),
                    Set.of(),
                    Set.of()
            )
    );

    public static final Set<Post> POSTS_BY_AUTHOR = Set.of(
            new Post(
                    1L,
                    POST_1_UUID,
                    USER.get(),
                    POST_1_URL,
                    POST_1_DESCRIPTION,
                    labels
            ),
            new Post(
                    2L,
                    POST_2_UUID,
                    USER.get(),
                    POST_2_URL,
                    POST_2_DESCRIPTION,
                    Set.of()
            )
    );

    public static final Set<Post> POSTS_BY_AUTHOR_NOT = Set.of(
            new Post(
                    3L,
                    POST_3_UUID,
                    USER_NOT.get(),
                    null,
                    null,
                    Set.of()
            ),
            new Post(
                    4L,
                    POST_4_UUID,
                    USER_NOT.get(),
                    null,
                    null,
                    labels
            )
    );

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    LabelService labelService;

    private PostFacade postFacade;

    @BeforeEach
    void setUp() {
        USER.get().getPosts().addAll(POSTS_BY_AUTHOR);
        postFacade =
                new PostFacade(postRepository, userRepository, labelService);
    }


    @Test
    void addPostWithLabelsToUserWithEmail() {
        //given
        when(labelService.addPostLabels(any())).thenReturn(labels);
        when(userRepository.findOneByEmailIgnoreCase(USER_EMAIL)).thenReturn(USER);

        //when
        var postDto =
                postFacade.addPostWithLabelsToUserWithEmail(ADD_POST_DTO, USER_EMAIL);

        //then
        assertEquals(USER_UUID, postDto.getAuthor().getUuid());
        assertEquals(POST_1_DESCRIPTION, postDto.getDescription());
        var labelStrings = SetExtension.map(labels, Label::getLabelName);
        assertEquals(labelStrings, postDto.getLabels());
    }

    @Test
    void getPostsByAuthorEmailNot() {
        //given
        when(userRepository.findOneByEmailIgnoreCase(USER_EMAIL)).thenReturn(USER);
        when(postRepository.findByAuthorNot(USER.get())).thenReturn(POSTS_BY_AUTHOR_NOT);

        //when
        var postDtos = postFacade.getPostsByAuthorEmailNot(USER_EMAIL);

        //then
        assertEquals(POSTS_BY_AUTHOR_NOT.size(), postDtos.size());
        assertEquals(
                SetExtension.map(POSTS_BY_AUTHOR_NOT, Post::getUuid),
                postDtos
                        .stream()
                        .map(PostDto::getUuid)
                        .collect(Collectors.toSet()));
        var forthPostDto = postDtos
                .stream()
                .filter(x -> x.getUuid().equals(POST_4_UUID))
                .findFirst();
        assertTrue(forthPostDto.isPresent());
        assertEquals(
                SetExtension.map(labels, Label::getLabelName),
                forthPostDto.get().getLabels());

    }

    @Test
    void getPostsByAuthorEmail() {
        //given
        when(userRepository.findOneByEmailIgnoreCase(USER_EMAIL)).thenReturn(USER);
        when(postRepository.findByAuthor(USER.get())).thenReturn(POSTS_BY_AUTHOR);

        //when
        var postDtos = postFacade.getPostsByAuthorEmail(USER_EMAIL);

        //then
        verify(userRepository, times(1)).findOneByEmailIgnoreCase(USER_EMAIL);
        verify(postRepository, times(1)).findByAuthor(USER.get());
        assertEquals(POSTS_BY_AUTHOR.size(), postDtos.size());
        assertEquals(
                SetExtension.map(POSTS_BY_AUTHOR, Post::getUuid),
                postDtos
                        .stream()
                        .map(PostDto::getUuid)
                        .collect(Collectors.toSet()));
        var firstPostDto = postDtos
                .stream()
                .filter(x -> x.getUuid().equals(POST_1_UUID))
                .findFirst();
        assertTrue(firstPostDto.isPresent());
        assertEquals(
                SetExtension.map(labels, Label::getLabelName),
                firstPostDto.get().getLabels());
    }

    @Test
    void getSuggestedPostsForUserWithEmail() {
        //given
        when(userRepository.findOneByEmailIgnoreCase(USER_EMAIL)).thenReturn(USER);
        when(labelService.getMostPopularLabels(USER.get().getPosts())).thenReturn(labels);
        when(postRepository.findByLabelsInAndAuthorNotIn(labels, Set.of(USER.get())))
                .thenReturn(POSTS_BY_AUTHOR_NOT);
        //when
        var suggestedPosts = postFacade.getSuggestedPostsForUserWithEmail(USER_EMAIL);

        //then
        assertEquals(
                SetExtension.map(POSTS_BY_AUTHOR_NOT, Post::getUuid),
                suggestedPosts
                        .stream()
                        .map(PostDto::getUuid)
                        .collect(Collectors.toSet())
        );
    }
}