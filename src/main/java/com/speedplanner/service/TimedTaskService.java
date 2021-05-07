package com.speedplanner.service;

import com.speedplanner.model.TimedTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface TimedTaskService {
    Page<TimedTask> getAllTimedTasksByStudyGroupId(Long studyGroupId, Pageable pageable);
    TimedTask getTimedTaskByIdAndStudyGroupId(Long studyGroupId, Long Id);
    TimedTask createTimedTask(Long studyGroupId, TimedTask TimedTask);
    TimedTask updateTimedTask(Long studyGroupId, Long TimedTaskId , TimedTask TimedTaskRequest);
    ResponseEntity<?> deleteTimedTask (Long studyGroupId, Long TimedTaskId);
}
