package com.abme.portal.domain;

import com.abme.portal.domain.user.User;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
@Entity
public class Post
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private User author;

    @Size(min = 5)
    private String URL;

    @Size(min = 5)
    @Column(length = 1000)
    private String description;
}
