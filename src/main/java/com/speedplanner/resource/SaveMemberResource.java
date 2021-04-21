package com.speedplanner.resource;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SaveMemberResource {
    @NotBlank
    @NotNull
    @Column(unique = true)
    @Size(max = 30)
    private String full_name;

    @NotNull
    @Column
    @Size(max = 100)
    private String description;
}
