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
public class SaveTimedTasksResource {

    @NotNull
    private boolean finished;

    @Column(nullable = false)
    private Date start_time;

    @Column(nullable = false)
    private Date finish_time;

    @NotNull
    @NotBlank
    @Size(max = 30)
    private String title;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String description;

}