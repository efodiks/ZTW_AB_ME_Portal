package com.abme.portal.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

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
}
