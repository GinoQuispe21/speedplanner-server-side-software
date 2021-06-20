package com.speedplanner.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class SaveTimedTaskResource {

    @NotNull
    private boolean finished;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime finishTime;

    @NotNull
    @NotBlank
    @Size(max = 30)
    private String title;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String description;

}
