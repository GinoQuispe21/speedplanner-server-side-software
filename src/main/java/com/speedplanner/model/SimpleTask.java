package com.speedplanner.model;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "simple_tasks")
@Getter
@Setter
public class SimpleTask {
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "notification_id", referencedColumnName = "id")
    private Notification notification;

    /*TODO:
    courses
    @ManyToOne
    @JoinColumn(name = "course_id" , nullable = false)
    @JsonIgnore
    private Course course;

    groups
    @ManyToOne
    @JoinColumn(name = "groups_id" , nullable = false)
    @JsonIgnore
    private Groups groups;
     */
}
