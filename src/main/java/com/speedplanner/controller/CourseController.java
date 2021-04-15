package com.speedplanner.controller;

import com.speedplanner.model.Course;
import com.speedplanner.resource.CourseResource;
import com.speedplanner.resource.SaveCourseResource;
import com.speedplanner.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CourseController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CourseService courseService;

    @GetMapping("/courses")
    public Page<CourseResource> getAllCourses(Pageable pageable) {
        Page<Course> coursePage = courseService.getAllCourses(pageable);
        List<CourseResource> resources = coursePage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/courses/{id}")
    public CourseResource getCourseById(@PathVariable(name = "id") Long courseId) {
        return convertToResource(courseService.getCourseById(courseId));
    }

    @PostMapping("/courses")
    public CourseResource createCourse(@Valid @RequestBody SaveCourseResource resource)  {
        Course course = convertToEntity(resource);
        return convertToResource(courseService.createCourse(course));
    }

    @PutMapping("/courses/{id}")
    public CourseResource updateCourse(@PathVariable(name = "id") Long courseId, @Valid @RequestBody SaveCourseResource resource) {
        Course course = convertToEntity(resource);
        return convertToResource(courseService.updateCourse(courseId, course));
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable(name = "id") Long courseId) {
        return courseService.deleteCourse(courseId);
    }

    private Course convertToEntity(SaveCourseResource resource) { return mapper.map(resource, Course.class); }

    private CourseResource convertToResource(Course entity) { return mapper.map(entity, CourseResource.class); }

}
