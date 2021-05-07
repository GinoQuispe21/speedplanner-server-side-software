package com.speedplanner.resource;

import com.speedplanner.model.Notification;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TimedTaskResource {
    private Long id;
    private boolean finished;
    private Date startTime;
    private Date finishTime;
    private String title;
    private String description;
}
