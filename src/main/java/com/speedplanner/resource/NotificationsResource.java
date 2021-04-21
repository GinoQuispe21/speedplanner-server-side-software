package com.speedplanner.resource;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NotificationsResource{
    private Long id;
    private String message;
    private Date reminder_date;
}
