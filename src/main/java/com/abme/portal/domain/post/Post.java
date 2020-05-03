package com.abme.portal.domain.post;

import com.abme.portal.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"})
@Entity
public class Post
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, updatable = false)
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @Size(min = 5)
    private String URL;

    @Size(min = 5)
    @Column(length = 1000)
    private String description;

    public static Post from(PostDto postDto) {
        return new Post(
                null,
                postDto.getUuid(),
                null,
                postDto.getURL(),
                postDto.getDescription()
        );
    }
}
