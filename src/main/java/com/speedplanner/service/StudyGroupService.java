package com.speedplanner.service;

import com.speedplanner.model.StudyGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface StudyGroupService {
    Page<StudyGroup> getAllStudyGroups(Pageable pageable);
    Page<StudyGroup> getAllStudyGroupsByCourseId(Pageable pageable, Long courseId);
    StudyGroup getStudyGroupByIdAndCourseId(Long studyGroupId, Long courseId);
    StudyGroup createStudyGroup(Long courseId, StudyGroup studyGroup);
    StudyGroup updateStudyGroup(Long courseId, Long studyGroupId, StudyGroup studyGroupRequest);
    ResponseEntity<?> deleteStudyGroup(Long courseId, Long studyGroupId);
}
