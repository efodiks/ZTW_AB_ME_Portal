package com.abme.portal.repository;

import com.abme.portal.domain.user.Role;
import com.abme.portal.domain.user.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Role, String> {
    Role findByName(RoleName name);
}
