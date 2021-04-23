package com.speedplanner.controller;

import com.speedplanner.model.SimpleTask;
import com.speedplanner.resource.SaveSimpleTasksResource;
import com.speedplanner.resource.SimpleTasksResource;
import com.speedplanner.service.SimpleTasksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Simple Tasks", description = "Simple Tasks API")
public class SimpleTaskController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private SimpleTasksService simpleTasksService;

    @Operation(summary = "Get all Simple Tasks", description = "Gets all the Simple Tasks from Speedplanner",
            tags = { "simple tasks" })
    @GetMapping("/simpletasks")
    public Page<SimpleTasksResource> getAllSimpleTasks(Pageable pageable) {
        Page<SimpleTask> simpleTaskPage = simpleTasksService.getAllSimpleTasks(pageable);
        List<SimpleTasksResource> resources = simpleTaskPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources , pageable , resources.size());
    }

    @Operation(summary = "Get a Simple Task by Id.", description = "Gets a particular Simple Task, given its Id.",
            tags = { "simple tasks" })
    @GetMapping("/simpletasks/{id}")
    public SimpleTasksResource getSimpleTaskById(@PathVariable(name = "id") Long simpleTaskId){
        return convertToResource(simpleTasksService.getSimpleTaskById(simpleTaskId));
    }

    @Operation(summary = "Create a Simple Task", description = "Creates a new Simple Task.", tags = { "simple tasks" })
    @PostMapping("/simpletasks")
    public SimpleTasksResource createSimpleTask(@Valid @RequestBody SaveSimpleTasksResource resource){
        SimpleTask simpleTask = convertToEntity(resource);
        return convertToResource(simpleTasksService.createSimpleTask(simpleTask));
    }

    @Operation(summary = "Update a Simple Task.", description = "Updates a Simple Task given its Id.",
            tags = { "simple tasks" })
    @PutMapping("/simpletasks/{id}")
    public SimpleTasksResource updateSimpleTask(@PathVariable(name = "id") Long simpleTaskId, @Valid @RequestBody SaveSimpleTasksResource resource){
        SimpleTask simpleTask = convertToEntity(resource);
        return convertToResource(simpleTasksService.updateSimpleTask(simpleTaskId , simpleTask));
    }

    @Operation(summary = "Delete a Simple Task", description = "Deletes a Simple Task, given its Id.",
            tags = { "simple tasks" })
    @DeleteMapping("/simpletasks/{id}")
    public ResponseEntity<?> deleteSimpleTask(@PathVariable(name = "id") Long simpleTaskId){
        return simpleTasksService.deleteSimpleTask(simpleTaskId);
    }

    private SimpleTask convertToEntity(SaveSimpleTasksResource resource) {return mapper.map(resource , SimpleTask.class); }

    private SimpleTasksResource convertToResource(SimpleTask entity) { return mapper.map(entity, SimpleTasksResource.class); }
}
