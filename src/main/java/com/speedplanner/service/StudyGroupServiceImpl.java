package com.speedplanner.service;

import com.speedplanner.exception.ResourceNotFoundException;
import com.speedplanner.model.StudyGroup;
import com.speedplanner.repository.StudyGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StudyGroupServiceImpl implements StudyGroupService {
    @Autowired
    private StudyGroupRepository studyGroupRepository;

    @Override
    public Page<StudyGroup> getAllStudyGroups(Pageable pageable) {
        return studyGroupRepository.findAll(pageable);
    }

    @Override
    public StudyGroup getStudyGroupById(Long groupId) {
        return studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "Id", groupId));
    }

    @Override
    public StudyGroup createStudyGroup(StudyGroup studyGroup) {
        return studyGroupRepository.save(studyGroup);
    }

    @Override
    public StudyGroup updateStudyGroup(Long groupId, StudyGroup studyGroupRequest) {
        StudyGroup studyGroup = studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "Id", groupId));
        studyGroup.setName(studyGroupRequest.getName());
        studyGroup.setDescription(studyGroupRequest.getDescription());
        return studyGroupRepository.save(studyGroup);
    }

    @Override
    public ResponseEntity<?> deleteStudyGroup(Long groupId) {
        StudyGroup studyGroup = studyGroupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group", "Id", groupId));
        studyGroupRepository.delete(studyGroup);
        return ResponseEntity.ok().build();
    }
}
