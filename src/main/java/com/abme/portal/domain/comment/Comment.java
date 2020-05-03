package com.abme.portal.domain.comment;

import com.abme.portal.domain.post.Post;
import com.abme.portal.domain.user.User;

import javax.persistence.*;

@Entity
public class Comment
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    Post post;

    @ManyToOne
    User author;

    @Column(nullable = false)
    String text;
}
