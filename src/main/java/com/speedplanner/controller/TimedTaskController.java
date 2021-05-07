package com.speedplanner.controller;

import com.speedplanner.model.TimedTask;
import com.speedplanner.resource.SaveTimedTaskResource;
import com.speedplanner.resource.TimedTaskResource;
import com.speedplanner.service.TimedTaskService;
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
@Tag(name = "Timed Tasks", description = "Timed Tasks API")
public class TimedTaskController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TimedTaskService timedTaskService;

    @Operation(summary = "Get all Timed Tasks", description = "Gets all the Timed Tasks for a specified Study Group, " +
            "given its Id.",
            tags = { "timed tasks", "study groups" })
    @GetMapping("/studyGroups/{studyGroupId}/timedTasks")
    public Page<TimedTaskResource> getAllTimedTasksByStudyGroupId(@PathVariable(name = "studyGroupId") Long studyGroupId,
                                                                  Pageable pageable) {
        Page<TimedTask> timedTaskPage = timedTaskService.getAllTimedTasksByStudyGroupId(studyGroupId, pageable);
        List<TimedTaskResource> resources = timedTaskPage.getContent().stream()
                .map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources , pageable , resources.size());
    }

    @Operation(summary = "Get a Timed Task by Id", description = "Gets the information of a particular Timed Task, " +
            "given its Id and its corresponding Study Group Id.", tags = { "timed tasks", "study groups" })
    @GetMapping("/studyGroups/{studyGroupId}/timedTasks/{id}")
    public TimedTaskResource getTimedTaskById(@PathVariable(name = "studyGroupId") Long studyGroupId,
                                              @PathVariable(name = "id") Long timedTaskId){
        return convertToResource(timedTaskService.getTimedTaskByIdAndStudyGroupId(studyGroupId, timedTaskId));
    }

    @Operation(summary = "Create a Timed Task", description = "Creates a new Timed Task for a specified Study " +
            "Group, given its Id.",
            tags = { "timed tasks", "study groups" })
    @PostMapping("/studyGroups/{studyGroupId}/timedTasks")
    public TimedTaskResource createTimedTask(@PathVariable(name = "studyGroupId") Long studyGroupId,
                                             @Valid @RequestBody SaveTimedTaskResource resource){
        TimedTask timedTask = convertToEntity(resource);
        return convertToResource(timedTaskService.createTimedTask(studyGroupId, timedTask));
    }

    @Operation(summary = "Update a  Timed Task", description = "Updates a particular Timed Task, given its Id and " +
            "its corresponding Study Group Id.",
            tags = { "timed tasks", "study groups" })
    @PutMapping("/studyGroups/{studyGroupId}/timedTasks/{id}")
    public TimedTaskResource updateTimedTask(@PathVariable(name = "studyGroupId") Long studyGroupId,
                                             @PathVariable(name = "id") Long timedTaskId,
                                             @Valid @RequestBody SaveTimedTaskResource resource){
        TimedTask timedTask = convertToEntity(resource);
        return convertToResource(timedTaskService.updateTimedTask(studyGroupId, timedTaskId , timedTask));
    }

    @Operation(summary = "Delete a Timed Task", description = "Deletes a Timed Task, given its Id, and its " +
            "corresponding Study Group Id.",
            tags = { "timed tasks", "study groups" })
    @DeleteMapping("/studyGroups/{studyGroupId}/timedTasks/{id}")
    public ResponseEntity<?> deleteTimedTask(@PathVariable(name = "studyGroupId") Long studyGroupId,
                                             @PathVariable(name = "id") Long timedTaskId){
        return timedTaskService.deleteTimedTask(studyGroupId, timedTaskId);
    }

    private TimedTask convertToEntity(SaveTimedTaskResource resource) {return mapper.map(resource , TimedTask.class); }

    private TimedTaskResource convertToResource(TimedTask entity) { return mapper.map(entity, TimedTaskResource.class); }
}
