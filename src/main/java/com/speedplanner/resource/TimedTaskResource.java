package com.speedplanner.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.speedplanner.model.Notification;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class TimedTaskResource {
    private Long id;
    private boolean finished;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime finishTime;
    private String title;
    private String description;
}
