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
    @GetMapping("/courses")
    public Page<CourseResource> getAllCourses(Pageable pageable) {
        Page<Course> coursePage = courseService.getAllCourses(pageable);
        List<CourseResource> resources = coursePage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Get Course by Id", description = "Get a course by his Id from speedplanner", tags = { "courses" })
    @GetMapping("/courses/{id}")
    public CourseResource getCourseById(@PathVariable(name = "id") Long courseId) {
        return convertToResource(courseService.getCourseById(courseId));
    }

    @Operation(summary = "Create Course", description = "Create a new course from Speedplanner", tags = { "courses" })
    @PostMapping("/courses")
    public CourseResource createCourse(@Valid @RequestBody SaveCourseResource resource)  {
        Course course = convertToEntity(resource);
        return convertToResource(courseService.createCourse(course));
    }

    @Operation(summary = "Update Course", description = "Update a course from Speedplanner", tags = { "courses" })
    @PutMapping("/courses/{id}")
    public CourseResource updateCourse(@PathVariable(name = "id") Long courseId, @Valid @RequestBody SaveCourseResource resource) {
        Course course = convertToEntity(resource);
        return convertToResource(courseService.updateCourse(courseId, course));
    }

    @Operation(summary = "Delete Course", description = "Delete a course from Speedplanner", tags = { "courses" })
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable(name = "id") Long courseId) {
        return courseService.deleteCourse(courseId);
    }

    private Course convertToEntity(SaveCourseResource resource) { return mapper.map(resource, Course.class); }

    private CourseResource convertToResource(Course entity) { return mapper.map(entity, CourseResource.class); }

}
