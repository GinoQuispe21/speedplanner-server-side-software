package com.speedplanner.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class SaveSimpleTaskResource {
    @NotNull
    private boolean finished;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date deadline;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String title;

    @NotNull
    @Lob
    private String description;
}
