package com.abme.portal.domain.post;

import com.abme.portal.domain.user.UserStubDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(of = {"uuid"})
@AllArgsConstructor
public class PostDto {

    private UUID uuid;

    private String URL;

    private String description;

    UserStubDto author;

    private Set<String> labels;

    public static PostDto fromPost(Post post) {
        return new PostDto(
                post.getUuid(),
                post.getURL(),
                post.getDescription(),
                post.getAuthor() != null ? UserStubDto.fromUser(post.getAuthor()) : null,
                post
                        .getLabels()
                        .stream()
                        .map(label -> label.getLabelName())
                        .collect(Collectors.toSet())
        );
    }
}