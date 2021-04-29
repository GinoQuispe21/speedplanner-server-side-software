package com.speedplanner.controller;

import com.speedplanner.model.Course;
import com.speedplanner.resource.CourseResource;
import com.speedplanner.resource.SaveCourseResource;
import com.speedplanner.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Courses", description = "Course API")
public class CourseController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CourseService courseService;

    @Operation(summary = "Get All Courses", description = "Get All Courses from Speedplanner", tags = { "courses" })
    @GetMapping("/users/{userId}/courses")
    public Page<CourseResource> getAllCoursesByUserId(@PathVariable(name = "userId") Long userId, Pageable pageable) {
        Page<Course> coursePage = courseService.getAllCoursesbyUserId(userId, pageable);
        List<CourseResource> resources = coursePage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Get Course by Id", description = "Get a course by his Id from speedplanner", tags = { "courses" })
    @GetMapping("/users/{userId}/courses/{courseId}")
    public CourseResource getCourseByIdAndUserId(@PathVariable(name = "userId") Long userId, @PathVariable(name = "courseId") Long courseId) {
        return convertToResource(courseService.getCourseByIdAndUserId(userId, courseId));
    }

    @Operation(summary = "Create Course", description = "Create a new course from Speedplanner", tags = { "courses" })
    @PostMapping("/users/{userId}/courses")
    public CourseResource createCourse(@PathVariable(name = "userId") Long userId, @Valid @RequestBody SaveCourseResource resource) {
        return convertToResource(courseService.createCourse(userId, convertToEntity(resource)));
    }

    @Operation(summary = "Update Course", description = "Update a course from Speedplanner", tags = { "courses" })
    @PutMapping("/users/{userId}/courses/{courseId}")
    public CourseResource updateCourse(@PathVariable(name = "userId") Long userId, @PathVariable(name = "courseId") Long courseId, @Valid @RequestBody SaveCourseResource resource) {
        return convertToResource(courseService.updateCourse(userId, courseId, convertToEntity(resource)));
    }

    @Operation(summary = "Delete Course", description = "Delete a course from Speedplanner", tags = { "courses" })
    @DeleteMapping("/users/{userId}/courses/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable(name = "userId") Long userId, @PathVariable(name = "courseId") Long courseId) {
        return courseService.deleteCourse(userId, courseId);
    }

    private Course convertToEntity(SaveCourseResource resource) { return mapper.map(resource, Course.class); }

    private CourseResource convertToResource(Course entity) { return mapper.map(entity, CourseResource.class); }

}
