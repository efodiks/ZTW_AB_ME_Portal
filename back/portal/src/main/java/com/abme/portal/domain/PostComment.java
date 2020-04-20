package com.abme.portal.domain;

import javax.persistence.*;

@Entity
public class PostComment
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    Post post;

    @ManyToOne
    User author;

    @Column(nullable = false)
    String text;
}
