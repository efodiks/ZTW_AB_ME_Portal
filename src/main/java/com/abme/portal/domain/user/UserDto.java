package com.abme.portal.domain.user;

import com.abme.portal.domain.post.PostDto;
import com.abme.portal.extensions.SetExtension;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"uuid"})
public class UserDto {
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String profilePhotoUrl;

    private Set<PostDto> posts;
    private Set<UserStubDto> following;
    private Set<UserStubDto> followedBy;

    public static UserDto fromUser(User user) {
        return new UserDto(
                user.getUuid(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUsername(),
                user.getProfilePhotoUrl(),
                SetExtension.map(user.getPosts(), PostDto::fromPost),
                SetExtension.map(user.getFollowing(), UserStubDto::fromUser),
                SetExtension.map(user.getFollowedBy(), UserStubDto::fromUser)
        );
    }
}
