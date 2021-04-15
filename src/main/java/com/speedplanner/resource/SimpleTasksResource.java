package com.speedplanner.resource;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SimpleTasksResource {
    private Long id;
    private boolean finished;
    private Date deadline;
    private String title;
    private String description;
}
