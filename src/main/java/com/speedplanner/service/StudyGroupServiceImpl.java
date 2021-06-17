package com.speedplanner.service;

import com.speedplanner.exception.ResourceNotFoundException;
import com.speedplanner.model.StudyGroup;
import com.speedplanner.model.User;
import com.speedplanner.repository.CourseRepository;
import com.speedplanner.repository.StudyGroupRepository;
import com.speedplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StudyGroupServiceImpl implements StudyGroupService {
    @Autowired
    private StudyGroupRepository studyGroupRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<StudyGroup> getAllStudyGroups(Pageable pageable) {
        return studyGroupRepository.findAll(pageable);
    }

    @Override
    public Page<StudyGroup> getAllStudyGroupsByCourseId(Long courseId, Pageable pageable) {
        if (!courseRepository.existsById(courseId))
            throw new ResourceNotFoundException("Course", "Id", courseId);
        return studyGroupRepository.findAllByCourseId(courseId, pageable);
    }

    @Override
    public StudyGroup getStudyGroupByIdAndCourseId(Long studyGroupId, Long courseId) {
        if (!courseRepository.existsById(courseId))
            throw new ResourceNotFoundException("Course not found with Id: "+courseId);
        return studyGroupRepository.findByIdAndCourseId(studyGroupId, courseId);
    }

    @Override
    public StudyGroup createStudyGroup(Long courseId, StudyGroup studyGroup) {
        return courseRepository.findById(courseId).map(course -> {
            studyGroup.setCourse(course);
            return studyGroupRepository.save(studyGroup);
        }).orElseThrow(
                () -> new ResourceNotFoundException("Course not found with Id: "+courseId));
    }

    @Override
    public StudyGroup updateStudyGroup(Long courseId, Long studyGroupId, StudyGroup studyGroupRequest) {
        return courseRepository.findById(courseId).map(course -> {
            StudyGroup studyGroup = studyGroupRepository.findById(studyGroupId)
                    .orElseThrow(() -> new ResourceNotFoundException("Group", "Id", studyGroupId));
            studyGroup.setName(studyGroupRequest.getName());
            studyGroup.setDescription(studyGroupRequest.getDescription());
            return studyGroupRepository.save(studyGroup);
        }).orElseThrow(() -> new ResourceNotFoundException("Course not found with Id: "+courseId));
    }

    @Override
    public ResponseEntity<?> deleteStudyGroup(Long courseId, Long studyGroupId) {
        return courseRepository.findById(courseId).map(course -> {
            StudyGroup studyGroup = studyGroupRepository.findById(studyGroupId).orElseThrow(() ->
                    new ResourceNotFoundException("Group", "Id", studyGroupId));
            studyGroupRepository.delete(studyGroup);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Course not found with Id: "+courseId));
    }
}
