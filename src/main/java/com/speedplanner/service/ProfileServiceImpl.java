package com.speedplanner.service;

import com.speedplanner.exception.ResourceNotFoundException;
import com.speedplanner.model.Profile;
import com.speedplanner.model.User;
import com.speedplanner.repository.ProfileRepository;
import com.speedplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Profile createProfile(Long userId, Profile profile) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found with Id: "+userId));
        profile.setUser(user);
        return profileRepository.save(profile);
    }

    @Override
    public Profile getProfileById(Long id) {
        return profileRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Profile not found with Id:"));
    }

    @Override
    public Profile updateProfile(Long userId, Profile profileRequest) {
        if(!userRepository.existsById(userId))
            throw new ResourceNotFoundException("User", "Id", userId);

        Profile profile = profileRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("Profile not found for user with Id: "+userId));
        profile.setAge(profileRequest.getAge());
        profile.setGender(profileRequest.getGender());
        profile.setFullName(profileRequest.getFullName());
        return profileRepository.save(profile);
    }

}
