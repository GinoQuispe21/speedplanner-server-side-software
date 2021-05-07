package com.speedplanner.resource;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SaveProfileResource {
    @NotBlank
    @NotNull
    @Size(max = 100)
    private String fullName;

    private int age;

    @NotBlank
    @NotNull
    @Size(max = 15)
    private String gender;
}
