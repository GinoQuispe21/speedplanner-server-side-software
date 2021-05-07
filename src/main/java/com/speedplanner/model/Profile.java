package com.speedplanner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "profiles")
@Data
public class Profile {
    @Id
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @NotNull
    @Size(max = 100)
    private String fullName;

    int age;

    @NotNull
    @NotBlank
    @Size(max = 15)
    private String gender;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
