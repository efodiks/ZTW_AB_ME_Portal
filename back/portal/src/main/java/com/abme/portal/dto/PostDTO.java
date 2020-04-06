package com.abme.portal.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDTO
{
    private Long id;

    @NotNull
    private UserDTO author;

    @Size(min = 5)
    private String URL;
}
