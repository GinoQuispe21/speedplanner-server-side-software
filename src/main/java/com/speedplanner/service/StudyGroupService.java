package com.speedplanner.service;

import com.speedplanner.model.StudyGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface StudyGroupService {
    Page<StudyGroup> getAllStudyGroups(Pageable pageable);
    StudyGroup getStudyGroupById(Long groupId);
    StudyGroup createStudyGroup(StudyGroup studyGroup);
    StudyGroup updateStudyGroup(Long groupId, StudyGroup studyGroupRequest);
    ResponseEntity<?> deleteStudyGroup(Long groupId);
}
