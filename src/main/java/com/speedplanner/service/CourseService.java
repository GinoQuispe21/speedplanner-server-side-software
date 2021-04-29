package com.speedplanner.service;

import com.speedplanner.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CourseService {
    ResponseEntity<?> deleteCourse(Long userId, Long courseId);
    Course updateCourse(Long userId, Long courseId, Course courseDetail);
    Course createCourse(Long userId, Course course);
    Course getCourseByIdAndUserId(Long userId, Long courseId);
    Page<Course> getAllCoursesbyUserId(Long userId, Pageable pageable);
}
