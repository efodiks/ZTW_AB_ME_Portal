package com.abme.portal.repository;

import com.abme.portal.domain.Post;
import com.abme.portal.domain.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    Iterable<Post> findByAuthor(User author);
    Iterable<Post> findByAuthorNot(User author);
}
