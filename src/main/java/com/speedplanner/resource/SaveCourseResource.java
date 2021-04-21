package com.speedplanner.resource;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SaveCourseResource {
    @NotBlank
    @NotNull
    @Size(max = 20)
    private String name;

    @NotNull
    @Size(max = 100)
    private String description;

    @NotBlank
    @NotNull
    @Size(max = 30)
    private String email;
}
