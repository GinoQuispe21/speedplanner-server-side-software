package com.speedplanner.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "notifications")
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Lob
    private String message;

    @Column(nullable = false , updatable = true)
    private Date reminder_date;

    //Relations

    //Simple_Task
    @OneToOne(mappedBy = "notification")
    private SimpleTask simpleTask;

    /*
    Timed_Task
    @OneToOne(mappedBy = "notification")
    private TimedTask timedTask;
    */
}