package com.speedplanner.service;

import com.speedplanner.model.TimedTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface TimedTasksService {
    Page<TimedTask> getAllTimedTasks (Pageable pageable);
    TimedTask createTimedTask (TimedTask TimedTask);
    TimedTask getTimedTaskById (Long Id);
    TimedTask updateTimedTask (Long TimedTaskId , TimedTask TimedTaskRequest);
    ResponseEntity<?> deleteTimedTask (Long TimedTaskId);
}
