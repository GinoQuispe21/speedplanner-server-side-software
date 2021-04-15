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

    @GetMapping("/times")
    public Page<TimeResource> getAllTimes(Pageable pageable) {
        Page<Time> timePage = timeService.getAllTimes(pageable);
        List<TimeResource> resources = timePage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/times/{id}")
    public TimeResource getTimeById(@PathVariable(name = "id") Long timeId) {
        return convertToResource(timeService.getTimeById(timeId));
    }

    @PostMapping("/times")
    public TimeResource createTime(@Valid @RequestBody SaveTimeResource resource)  {
        Time time = convertToEntity(resource);
        return convertToResource(timeService.createTime(time));
    }

    @PutMapping("/times/{id}")
    public TimeResource updateTime(@PathVariable(name = "id") Long timeId, @Valid @RequestBody SaveTimeResource resource) {
        Time time = convertToEntity(resource);
        return convertToResource(timeService.updateTime(timeId, time));
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable(name = "id") Long timeId) {
        return timeService.deleteTime(timeId);
    }

    private Time convertToEntity(SaveTimeResource resource) { return mapper.map(resource, Time.class); }

    private TimeResource convertToResource(Time entity) { return mapper.map(entity, TimeResource.class); }

}
