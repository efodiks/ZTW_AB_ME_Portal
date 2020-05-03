package com.abme.portal.domain;

import com.abme.portal.domain.user.User;

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
