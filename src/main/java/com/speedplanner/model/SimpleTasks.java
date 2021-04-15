package com.speedplanner.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "simple_task")
@Getter
@Setter
public class SimpleTasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private boolean finished;

    @Column(nullable = false)
    private Date deadline;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String title;

    @NotNull
    @Lob
    private String description;

    //Relations

    //Notifications
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "notifications_id" , referencedColumnName = "id")
    private Notifications notifications;


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
