package com.speedplanner.service;

import com.speedplanner.exception.ResourceNotFoundException;
import com.speedplanner.model.Group;
import com.speedplanner.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Page<Group> getAllGroups(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }

    @Override
    public Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "Id", groupId));
    }

    @Override
    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Group updateGroup(Long groupId, Group groupRequest) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "Id", groupId));
        group.setName(groupRequest.getName());
        group.setDescription(groupRequest.getDescription());
        return groupRepository.save(group);
    }

    @Override
    public ResponseEntity<?> deleteGroup(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group", "Id", groupId));
        groupRepository.delete(group);
        return ResponseEntity.ok().build();
    }
}
