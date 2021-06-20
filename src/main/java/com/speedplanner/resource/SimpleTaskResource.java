package com.speedplanner.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class SimpleTaskResource {
    private Long id;
    private boolean finished;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date deadline;
    private String title;
    private String description;
}
