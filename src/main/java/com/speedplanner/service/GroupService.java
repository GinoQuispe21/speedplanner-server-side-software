package com.speedplanner.service;

import com.speedplanner.model.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface GroupService {
    Page<Group> getAllUsers(Pageable pageable);
    Group getGroupById(Long groupId);
    Group createGroup(Group group);
    Group updateGroup(Long groupId, Group groupRequest);
    ResponseEntity<?> deleteGroup(Long groupId);
}
