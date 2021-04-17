package com.speedplanner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "times")
@Data
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Size(max = 20)
    private String day;

    @NotBlank
    @NotNull
    private Date startTime;

    @NotBlank
    @NotNull
    private Date finishTime;

    //Many to one Relationship betwen Course and Time
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnore
    private Course course;

}
