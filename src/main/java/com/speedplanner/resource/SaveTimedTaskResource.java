package com.speedplanner.resource;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class SaveTimedTaskResource {

    @NotNull
    private boolean finished;

    @NotNull
    private Date startTime;

    @NotNull
    private Date finishTime;

    @NotNull
    @NotBlank
    @Size(max = 30)
    private String title;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String description;

}
