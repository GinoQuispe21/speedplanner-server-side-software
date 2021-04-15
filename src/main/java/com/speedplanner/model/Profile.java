package com.speedplanner.model;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Size(max = 100)
    private String full_name;

    int age;

    @NotNull
    @NotBlank
    @Size(max = 15)
    private String gender;

    @OneToOne(mappedBy = "profiles")
    private User user;
}
