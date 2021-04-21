package com.speedplanner.resource;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class SaveTimeResource {
    @NotBlank
    @NotNull
    @Size(max = 20)
    private String day;

    @NotBlank
    @NotNull
    private Date startTime;

    @NotBlank
    @NotNull
    private Date finishTime;
}
