package com.speedplanner.controller;

import com.speedplanner.model.Profile;
import com.speedplanner.resource.ProfileResource;
import com.speedplanner.resource.SaveProfileResource;
import com.speedplanner.service.ProfileService;
import com.speedplanner.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/users/{userId}")
@Tag(name = "Profiles", description = "User API")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @Autowired
    ModelMapper mapper;

    @Operation(summary = "Create a profile by User Id", description = "Create profile from User by his Id", tags = {"profiles"})
    @PostMapping("/profile")
    public ProfileResource createProfile(@Valid @RequestBody SaveProfileResource resource,
                                         @PathVariable(name = "userId") Long id) {
        Profile profile = profileService.createProfile(id, convertToEntity(resource));
        userService.setUserProfile(id, profile);
        return convertToResource(profileService.createProfile(id, profile));
    }

    @Operation(summary = "Get profile by User Id ", description = "Get a profile from User by his Id", tags = {"profiles"})
    @GetMapping("/profile")
    public ProfileResource getProfile(@PathVariable(name = "userId") Long userId) {
        return convertToResource(profileService.getProfileById(userId));
    }

    @Operation(summary = "Update profile by User Id ", description = "Update a profile from User by his Id", tags = { "profiles" })
    @PutMapping("/profile")
    public ProfileResource updateProfile(@PathVariable(name = "userId") Long userId,
                                         @Valid @RequestBody SaveProfileResource resource) {
        Profile profile = profileService.updateProfile(userId, convertToEntity(resource));
        return convertToResource(profileService.updateProfile(userId, profile));
    }

    private Profile convertToEntity(SaveProfileResource resource){ return mapper.map(resource, Profile.class);}
    private ProfileResource convertToResource(Profile entity){return mapper.map(entity, ProfileResource.class); }
}
