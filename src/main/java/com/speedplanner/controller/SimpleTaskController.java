package com.speedplanner.controller;

import com.speedplanner.model.SimpleTask;
import com.speedplanner.resource.SaveSimpleTasksResource;
import com.speedplanner.resource.SimpleTasksResource;
import com.speedplanner.service.SimpleTasksService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class SimpleTaskController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private SimpleTasksService simpleTasksService;

    @GetMapping("/simpletasks")
    public Page<SimpleTasksResource> getAllSimpleTasks(Pageable pageable) {
        Page<SimpleTask> simpleTaskPage = simpleTasksService.getAllSimpleTasks(pageable);
        List<SimpleTasksResource> resources = simpleTaskPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources , pageable , resources.size());
    }

    @GetMapping("/simpletasks/{id}")
    public SimpleTasksResource getSimpleTaskById(@PathVariable(name = "id") Long simpleTaskId){
        return convertToResource(simpleTasksService.getSimpleTaskById(simpleTaskId));
    }

    @PostMapping("/simpletasks")
    public SimpleTasksResource createSimpleTask(@Valid @RequestBody SaveSimpleTasksResource resource){
        SimpleTask simpleTask = convertToEntity(resource);
        return convertToResource(simpleTasksService.createSimpleTask(simpleTask));
    }

    @PutMapping("/simpletasks/{id}")
    public SimpleTasksResource updateSimpleTask(@PathVariable(name = "id") Long simpleTaskId, @Valid @RequestBody SaveSimpleTasksResource resource){
        SimpleTask simpleTask = convertToEntity(resource);
        return convertToResource(simpleTasksService.updateSimpleTask(simpleTaskId , simpleTask));
    }

    @DeleteMapping("/simpletasks/{id}")
    public ResponseEntity<?> deleteSimpleTask(@PathVariable(name = "id") Long simpleTaskId){
        return simpleTasksService.deleteSimpleTask(simpleTaskId);
    }

    private SimpleTask convertToEntity(SaveSimpleTasksResource resource) {return mapper.map(resource , SimpleTask.class); }

    private SimpleTasksResource convertToResource(SimpleTask entity) { return mapper.map(entity, SimpleTasksResource.class); }
}
