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
    //TODO:

    //Simple_Task
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "simpleTask_id" , referencedColumnName = "id")
    private SimpleTask simpleTask;

    /*
    Timed_Task
    @OneToOne(mappedBy = "notification")
    private TimedTask timedTask;
    */
}