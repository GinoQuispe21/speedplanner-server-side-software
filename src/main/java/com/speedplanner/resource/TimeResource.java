package com.speedplanner.resource;

import lombok.Data;

import java.util.Date;

@Data
public class TimeResource {
    private Long id;
    private String day;
    private Date startTime;
    private Date finishTime;
}
