package com.abme.portal.domain.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(of={"uuid"})
@AllArgsConstructor
public class PostDto {

    private UUID uuid;
    private String URL;
    private String description;

    public static PostDto fromPost(Post post) {
        return new PostDto(
                post.getUuid(),
                post.getURL(),
                post.getDescription()
        );
    }
}
