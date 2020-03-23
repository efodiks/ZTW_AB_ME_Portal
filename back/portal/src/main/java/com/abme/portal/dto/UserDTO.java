package com.abme.portal.dto;

import com.abme.portal.domain.Authority;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {

    private Long id;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @NotNull
    @Size(min = 5, max = 60)
    private String password;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    private Set<Authority> authorities = new HashSet<>();
}
