package com.speedplanner.service;

import com.speedplanner.exception.ResourceNotFoundException;
import com.speedplanner.model.Course;
import com.speedplanner.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public ResponseEntity<?> deleteCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course", "Id", courseId));
        courseRepository.delete(course);
        return ResponseEntity.ok().build();
    }

    @Override
    public Course updateCourse(Long courseId, Course courseRequest) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", courseId));
        course.setName(courseRequest.getName());
        course.setDescription(courseRequest.getDescription());
        course.setEmail(courseRequest.getEmail());
        return courseRepository.save(course);
    }

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", courseId));
    }

    @Override
    public Page<Course> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }
}
