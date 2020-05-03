package com.abme.portal.domain.authentication;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginDto {

    @Email
    @NotNull
    @Size(min = 1, max = 50)
    private String email;

    @NotNull
    @Size(min = 4, max = 100)
    private String password;
}
