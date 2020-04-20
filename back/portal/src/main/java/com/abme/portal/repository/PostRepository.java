package com.abme.portal.repository;

import com.abme.portal.domain.Post;
import com.abme.portal.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    Iterable<Post> findByAuthor(User author);
    Iterable<Post> findByAuthorNot(User author);
}
