package com.speedplanner.service;

import com.speedplanner.exception.ResourceNotFoundException;
import com.speedplanner.model.Profile;
import com.speedplanner.model.User;
import com.speedplanner.repository.ProfileRepository;
import com.speedplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Profile createProfile(Long userId, Profile profile) {
        return userRepository.findById(userId).map(user -> {
            profile.setUser(user);
            profile.setId(userId);
            return profileRepository.save(profile);
        }).orElseThrow( () -> new ResourceNotFoundException("User not found with Id: "+userId));
    }

    @Override
    public Profile getProfileById(Long id) {
        return profileRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Profile not found with Id:"));
    }

    @Override
    public Page<Profile> getAllProfiles(Pageable pageable) {
        return profileRepository.findAll(pageable);
    }

    @Override
    public Profile updateProfile(Long profileId, Profile profileRequest) {
        if(!userRepository.existsById(profileId))
            throw new ResourceNotFoundException("User", "Id", profileId);
        return profileRepository.findById(profileId).map(profile -> {
            profile.setAge(profileRequest.getAge());
            profile.setGender(profileRequest.getGender());
            profile.setFull_name(profileRequest.getFull_name());
            return profileRepository.save(profile);
        }).orElseThrow(() -> new ResourceNotFoundException("Profile", "Id", profileId));
    }

    @Override
    public ResponseEntity<?> deleteProfile(Long id) {
        return profileRepository.findById(id).map(profile -> {
            profileRepository.delete(profile);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Profile not found with Id: "+id));
    }
}
