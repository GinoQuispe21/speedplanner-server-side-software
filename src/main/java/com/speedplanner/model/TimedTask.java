package com.speedplanner.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "timed_tasks")
@Getter
@Setter
public class TimedTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private boolean finished;

    @Column(nullable = false)
    private Date start_time;

    @Column(nullable = false)
    private Date finish_time;

    @NotNull
    @NotBlank
    @Size(max = 30)
    private String title;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String description;

    //Relations

    //Notifications
    /*
    @OneToOne(mappedBy = "timedTask")
    private Notification notification;
    */

    /*
    courses
    @ManyToOne
    @JoinColumn(name = "course_id" , nullable = false)
    @JsonIgnore
    private Course course;

    groups
    @ManyToOne
    @JoinColumn(name = "groups_id" , nullable = false)
    @JsonIgnore
    private Groups groups:
     */
}
