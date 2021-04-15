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
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Lob
    private String text;

    @Column(nullable = false , updatable = true)
    private Date reminder_date;

    //Relations

    //Simple_Task
    @OneToOne(mappedBy = "notifications")
    private SimpleTasks simpleTask;

    /*
    Timed_Task
    @OneToOne(mappedBy = "notifications")
    private TimedTask timedTask;
    */
}