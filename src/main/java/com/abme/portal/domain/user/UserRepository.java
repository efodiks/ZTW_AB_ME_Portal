package com.abme.portal.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByUuid(UUID uuid);
}
