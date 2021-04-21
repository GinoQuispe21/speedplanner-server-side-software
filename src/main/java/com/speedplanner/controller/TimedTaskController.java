package com.speedplanner.controller;

import com.speedplanner.model.TimedTask;
import com.speedplanner.resource.SaveTimedTasksResource;
import com.speedplanner.resource.TimedTasksResource;
import com.speedplanner.service.TimedTasksService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class TimedTaskController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TimedTasksService timedTasksService;

    @GetMapping("/timedtasks")
    public Page<TimedTasksResource> getAllTimedTasks(Pageable pageable) {
        Page<TimedTask> timedTaskPage = timedTasksService.getAllTimedTasks(pageable);
        List<TimedTasksResource> resources = timedTaskPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources , pageable , resources.size());
    }

    @GetMapping("/timedtasks/{id}")
    public TimedTasksResource getTimedTaskById(@PathVariable(name = "id") Long timedTaskId){
        return convertToResource(timedTasksService.getTimedTaskById(timedTaskId));
    }

    @PostMapping("/timedtasks")
    public TimedTasksResource createTimedTask(@Valid @RequestBody SaveTimedTasksResource resource){
        TimedTask timedTask = convertToEntity(resource);
        return convertToResource(timedTasksService.createTimedTask(timedTask));
    }

    @PutMapping("/timedtasks/{id}")
    public TimedTasksResource updateTimedTask(@PathVariable(name = "id") Long timedTaskId, @Valid @RequestBody SaveTimedTasksResource resource){
        TimedTask timedTask = convertToEntity(resource);
        return convertToResource(timedTasksService.updateTimedTask(timedTaskId , timedTask));
    }

    @DeleteMapping("/timedtasks/{id}")
    public ResponseEntity<?> deleteTimedTask(@PathVariable(name = "id") Long timedTaskId){
        return timedTasksService.deleteTimedTask(timedTaskId);
    }

    private TimedTask convertToEntity(SaveTimedTasksResource resource) {return mapper.map(resource , TimedTask.class); }

    private TimedTasksResource convertToResource(TimedTask entity) { return mapper.map(entity, TimedTasksResource.class); }
}
