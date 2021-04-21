package com.speedplanner.resource;

import com.speedplanner.model.Notification;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TimedTasksResource {
    private Long id;
    private boolean finished;
    private Date start_time;
    private Date finish_time;
    private String title;
    private String description;
}
