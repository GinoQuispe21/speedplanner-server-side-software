package com.speedplanner.service;

import com.speedplanner.exception.ResourceNotFoundException;
import com.speedplanner.model.Course;
import com.speedplanner.model.StudyGroup;
import com.speedplanner.repository.CourseRepository;
import com.speedplanner.repository.StudyGroupRepository;
import com.speedplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyGroupRepository studyGroupRepository;

    @Override
    public ResponseEntity<?> deleteCourse(Long userId, Long courseId) {
        return courseRepository.findByIdAndUserId(courseId, userId).map(course ->{
            courseRepository.delete(course);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(" Course not found with Id " + courseId + " and UserId " + userId));
    }

    @Override
    public Course updateCourse(Long userId, Long courseId, Course courseDetail) {
        if(!userRepository.existsById(userId))
            throw new ResourceNotFoundException("User", "Id", userId);

        return courseRepository.findById(courseId).map(course -> {
            course.setName(courseDetail.getName());
            course.setDescription(courseDetail.getDescription());
            course.setEmail(courseDetail.getEmail());
            course.setColor(courseDetail.getColor());
            return courseRepository.save(course);
        }).orElseThrow(() -> new ResourceNotFoundException("Course", "Id", courseId));

    }

    @Override
    public Course createCourse(Long userId, Course course) {
        return userRepository.findById(userId).map(user -> {
            course.setUser(user);
            StudyGroup studyGroup = new StudyGroup();
            studyGroup.setCourse(course);
            studyGroup.setName("Grupo personal");
            studyGroup.setDescription("Grupo para uso personal de: "+user.getProfile().getFullName());
            studyGroupRepository.save(studyGroup);
            return courseRepository.save(course);
        }).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
    }


    public void AssignStudyGroup(Long userId, Long courseId){

    }

    @Override
    public Course getCourseByIdAndUserId(Long userId, Long courseId) {
        return courseRepository.findByIdAndUserId(courseId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with Id" + courseId +
                                "and UserId " + userId
                ));
    }

    @Override
    public Page<Course> getAllCoursesbyUserId(Long userId, Pageable pageable) {
        return courseRepository.findByUserId(userId, pageable);
    }
}
