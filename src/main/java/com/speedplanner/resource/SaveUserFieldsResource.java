package com.speedplanner.resource;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SaveUserFieldsResource {
    @NotBlank
    @NotNull
    @Size(max = 20)
    private String username;

    @NotBlank
    @NotNull
    @Size(max = 40)
    private String email;
    private String role;
}
