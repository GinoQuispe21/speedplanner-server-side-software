package com.speedplanner.service;

import com.speedplanner.model.Profile;
import org.springframework.http.ResponseEntity;

public interface ProfileService {
    Profile createProfile(Long userId, Profile profile);
    Profile getProfileById(Long id);
    Profile updateProfile(Long profileId, Profile profileRequest);
}
