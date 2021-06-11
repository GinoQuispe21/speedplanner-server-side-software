package com.speedplanner.service;

import com.speedplanner.model.Profile;
import com.speedplanner.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User createUser(User user);
    User getUserById(Long userId);
    Page<User> getAllUsers(Pageable pageable);
    User updateUser(Long userId, User userRequest);
    User changePassword(Long userId, User userRequest);
    ResponseEntity<?> deleteUser(Long userId);
    User setUserProfile(Long userId, Profile profile);
    User getUserByUsername(String username);
    User updateUserFields(Long userId, User userFieldsRequest);
}
