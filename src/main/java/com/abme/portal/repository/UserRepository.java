package com.abme.portal.repository;

import com.abme.portal.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findOneByEmailIgnoreCase(String email);
}
