package com.speedplanner.resource;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class SaveNotificationsResource {

    @NotNull
    @Lob
    private String message;

    @NotNull
    private Date reminder_date;
}
