package com.speedplanner.service;

import com.speedplanner.model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ProfileService {
    Profile createProfile(Long userId, Profile profile);
    Profile getProfileById(Long id);
    Page<Profile> getAllProfiles(Pageable pageable);
    Profile updateProfile(Long profileId, Profile profileRequest);
    ResponseEntity<?> deleteProfile(Long id);
}
