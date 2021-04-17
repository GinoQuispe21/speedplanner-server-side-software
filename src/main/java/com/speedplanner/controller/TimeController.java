package com.speedplanner.controller;

import com.speedplanner.model.Time;
import com.speedplanner.resource.SaveTimeResource;
import com.speedplanner.resource.TimeResource;
import com.speedplanner.service.TimeService;
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
public class TimeController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TimeService timeService;

    @GetMapping("/courses/{courseId}/times")
    public Page<TimeResource> getAllTimesByCourseId(@PathVariable(name = "courseId") Long courseId, Pageable pageable) {
        Page<Time> timePage = timeService.getAllTimesByCourseId(courseId, pageable);
        List<TimeResource> resources = timePage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/courses/{courseId}/times/{timeId}")
    public TimeResource getTimeByIdAndCourseId(@PathVariable(name = "courseId") Long courseId, @PathVariable(name = "timeId") Long timeId) {
        return convertToResource(timeService.getTimeByIdAndCourseId(courseId, timeId));
    }

    @PostMapping("/courses/{courseId}/times")
    public TimeResource createTime(@PathVariable(name = "courseId") Long courseId, @Valid @RequestBody SaveTimeResource resource) {
        return convertToResource(timeService.createTime(courseId, convertToEntity(resource)));
    }

    @PutMapping("/courses/{courseId}/times/{timeId}")
    public TimeResource updateTime(@PathVariable(name = "courseId") Long courseId, @PathVariable(name = "timeId") Long timeId, @Valid @RequestBody SaveTimeResource resource) {
        return convertToResource(timeService.updateTime(courseId, timeId, convertToEntity(resource)));
    }

    @DeleteMapping("/courses/{courseId}/times/{timeId}")
    public ResponseEntity<?> deleteTime(@PathVariable(name = "courseId") Long courseId, @PathVariable(name = "timeId") Long timeId) {
        return timeService.deleteTime(courseId, timeId);
    }
    private Time convertToEntity(SaveTimeResource resource) { return mapper.map(resource, Time.class); }

    private TimeResource convertToResource(Time entity) { return mapper.map(entity, TimeResource.class); }

}
