package com.abme.portal.domain.authentication;

import com.abme.portal.domain.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    private JwtToken token;
    private UserDto userDto;
}
