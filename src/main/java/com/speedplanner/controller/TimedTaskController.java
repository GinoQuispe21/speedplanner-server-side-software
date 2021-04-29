package com.speedplanner.controller;

import com.speedplanner.model.TimedTask;
import com.speedplanner.resource.SaveTimedTasksResource;
import com.speedplanner.resource.TimedTasksResource;
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

    @Operation(summary = "Get all Timed Tasks", description = "Gets all the Timed Tasks from Speedplanner",
            tags = { "timed tasks" })
    @GetMapping("/timedTasks")
    public Page<TimedTasksResource> getAllTimedTasks(Pageable pageable) {
        Page<TimedTask> timedTaskPage = timedTaskService.getAllTimedTasks(pageable);
        List<TimedTasksResource> resources = timedTaskPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources , pageable , resources.size());
    }

    @Operation(summary = "Get a Timed Task by Id", description = "Gets the information of a particular Timed Task, " +
            "given its Id", tags = { "timed tasks" })
    @GetMapping("/timedTasks/{id}")
    public TimedTasksResource getTimedTaskById(@PathVariable(name = "id") Long timedTaskId){
        return convertToResource(timedTaskService.getTimedTaskById(timedTaskId));
    }

    @Operation(summary = "Create a Timed Task", description = "Creates a new Timed Task", tags = { "timed tasks" })
    @PostMapping("/timedTasks")
    public TimedTasksResource createTimedTask(@Valid @RequestBody SaveTimedTasksResource resource){
        TimedTask timedTask = convertToEntity(resource);
        return convertToResource(timedTaskService.createTimedTask(timedTask));
    }

    @Operation(summary = "Update a  Timed Task", description = "Updates a particular Timed Task, given its Id.",
            tags = { "timed tasks" })
    @PutMapping("/timedTasks/{id}")
    public TimedTasksResource updateTimedTask(@PathVariable(name = "id") Long timedTaskId, @Valid @RequestBody SaveTimedTasksResource resource){
        TimedTask timedTask = convertToEntity(resource);
        return convertToResource(timedTaskService.updateTimedTask(timedTaskId , timedTask));
    }

    @Operation(summary = "Delete a Timed Task", description = "Deletes a Timed Task, given its Id.",
            tags = { "timed tasks" })
    @DeleteMapping("/timedTasks/{id}")
    public ResponseEntity<?> deleteTimedTask(@PathVariable(name = "id") Long timedTaskId){
        return timedTaskService.deleteTimedTask(timedTaskId);
    }

    private TimedTask convertToEntity(SaveTimedTasksResource resource) {return mapper.map(resource , TimedTask.class); }

    private TimedTasksResource convertToResource(TimedTask entity) { return mapper.map(entity, TimedTasksResource.class); }
}
