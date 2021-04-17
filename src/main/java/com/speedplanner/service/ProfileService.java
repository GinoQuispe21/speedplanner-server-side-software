package com.speedplanner.service;

import com.speedplanner.model.Profile;
import com.speedplanner.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ProfileService {
    Profile createProfile(Long userId, User user);
    Profile getProfileById(Long id);
    Page<Profile> getAllProfiles(Pageable pageable);
    Profile updateProfile(Long profileId, Profile profileRequest);
    ResponseEntity<?> deleteProfile(Long id);
}
