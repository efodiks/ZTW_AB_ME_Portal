package com.abme.portal.domain.post;

import com.abme.portal.domain.label.Label;
import com.abme.portal.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Set<Post> findByAuthor(User author);
    Set<Post> findByAuthorNot(User author);
    Set<Post> findByAuthor_Uuid(UUID authorUuid);
    Set<Post> findByLabelsInAndAuthorNotIn(Set<Label> labels, Set<User> author);
}
