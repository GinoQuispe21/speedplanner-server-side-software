package com.speedplanner.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
public class SaveTimeResource {
    @NotBlank
    @NotNull
    @Size(max = 20)
    private String day;

    @NotBlank
    @NotNull
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @NotBlank
    @NotNull
    @JsonFormat(pattern = "HH:mm")
    private LocalTime finishTime;
}
