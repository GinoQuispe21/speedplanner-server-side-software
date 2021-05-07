package com.speedplanner.resource;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class SaveSimpleTaskResource {
    @NotNull
    private boolean finished;

    @NotNull
    private Date deadline;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String title;

    @NotNull
    @Lob
    private String description;
}
