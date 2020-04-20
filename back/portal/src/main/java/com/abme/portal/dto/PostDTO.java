package com.abme.portal.dto;

import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDTO
{
    private Long id;

    @Size(min = 5)
    private String URL;

    @Size(min = 5)
    private String description;
}
