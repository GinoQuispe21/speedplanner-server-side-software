package com.speedplanner.resource;

import lombok.Data;

@Data
public class ProfileResource {
    private Long id;
    private String fullName;
    private int age;
    private String gender;
}
