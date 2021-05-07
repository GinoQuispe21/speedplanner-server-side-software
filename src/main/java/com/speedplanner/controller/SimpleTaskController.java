package com.speedplanner.controller;

import com.speedplanner.model.SimpleTask;
import com.speedplanner.resource.SaveSimpleTasksResource;
import com.speedplanner.resource.SimpleTasksResource;
import com.speedplanner.service.SimpleTaskService;
import com.speedplanner.service.StudyGroupService;
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
    private SimpleTaskService simpleTaskService;
    
    @Operation(summary = "Get all Simple Tasks", description = "Gets all the Simple Tasks for a specified Study " +
            "Group, given its Id.",
            tags = { "simple tasks", "study groups" })
    @GetMapping("/studyGroups/{studyGroupId}/simpleTasks")
    public Page<SimpleTasksResource> getAllSimpleTasksByStudyGroupId(@PathVariable(name = "studyGroupId") Long studyGroupId,
                                                                     Pageable pageable) {
        Page<SimpleTask> simpleTaskPage = simpleTaskService.getAllSimpleTasksByStudyGroupId(studyGroupId,pageable);
        List<SimpleTasksResource> resources = simpleTaskPage.getContent().stream()
                .map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources , pageable , resources.size());
    }

    @Operation(summary = "Get a Simple Task by Id.", description = "Gets a particular Simple Task, given its Id and" +
            " its corresponding Study Group Id..",
            tags = { "simple tasks", "study groups" })
    @GetMapping("/studyGroups/{studyGroupId}/simpleTasks/{id}")
    public SimpleTasksResource getSimpleTaskById(@PathVariable(name = "studyGroupId") Long studyGroupId,
                                                 @PathVariable(name = "id") Long simpleTaskId){
        return convertToResource(simpleTaskService.getSimpleTaskByIdAndStudyGroupId(studyGroupId, simpleTaskId));
    }

    @Operation(summary = "Create a Simple Task", description = "Creates a new Simple Task given its" +
            "corresponding Study Group Id.",
            tags = { "simple tasks", "study groups" })
    @PostMapping("/studyGroups/{studyGroupId}/simpleTasks")
    public SimpleTasksResource createSimpleTask(@PathVariable(name = "studyGroupId") Long studyGroupId,
                                                @Valid @RequestBody SaveSimpleTasksResource resource){
        SimpleTask simpleTask = convertToEntity(resource);
        return convertToResource(simpleTaskService.createSimpleTask(studyGroupId, simpleTask));
    }

    @Operation(summary = "Update a Simple Task.", description = "Updates a Simple Task given its Id and its" +
            "corresponding Study Group Id.",
            tags = { "simple tasks", "study groups" })
    @PutMapping("/studyGroups/{studyGroupId}/simpleTasks/{id}")
    public SimpleTasksResource updateSimpleTask(@PathVariable(name = "studyGroupId") Long studyGroupId,
                                                @PathVariable(name = "id") Long simpleTaskId,
                                                @Valid @RequestBody SaveSimpleTasksResource resource){
        SimpleTask simpleTask = convertToEntity(resource);
        return convertToResource(simpleTaskService.updateSimpleTask(studyGroupId, simpleTaskId, simpleTask));
    }

    @Operation(summary = "Delete a Simple Task", description = "Deletes a Simple Task, given its Id and its" +
            "corresponding Study Group Id.",
            tags = { "simple tasks", "study groups" })
    @DeleteMapping("/studyGroups/{studyGroupId}/simpleTasks/{id}")
    public ResponseEntity<?> deleteSimpleTask(@PathVariable(name = "studyGroupId") Long studyGroupId,
                                              @PathVariable(name = "id") Long simpleTaskId){
        return simpleTaskService.deleteSimpleTask(studyGroupId, simpleTaskId);
    }

    private SimpleTask convertToEntity(SaveSimpleTasksResource resource) {return mapper.map(resource , SimpleTask.class); }

    private SimpleTasksResource convertToResource(SimpleTask entity) { return mapper.map(entity, SimpleTasksResource.class); }
}
