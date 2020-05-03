package com.abme.portal.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"uuid"})
public class UserRegisterDto {

    @NotNull
    private UUID uuid;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Size(max = 50)
    private String username;

    @Size(max = 50)
    private String password;

    @Size(min = 5)
    private String profilePhotoUrl;
}