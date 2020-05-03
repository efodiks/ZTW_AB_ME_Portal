package com.abme.portal.domain.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@EqualsAndHashCode(of={"uuid"})
@AllArgsConstructor
public class AddPostDto {

    @NotNull
    private UUID uuid;

    @Size(min = 5)
    private String URL;

    @Size(min = 5)
    private String description;

    public static AddPostDto fromPost(Post post) {
        return new AddPostDto(
                post.getUuid(),
                post.getURL(),
                post.getDescription()
        );
    }
}
