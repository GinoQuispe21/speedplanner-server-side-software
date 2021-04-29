package com.speedplanner.service;

import com.speedplanner.model.SimpleTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface SimpleTaskService {
    Page<SimpleTask> getAllSimpleTasks (Pageable pageable);
    SimpleTask createSimpleTask (SimpleTask simpleTask);
    SimpleTask getSimpleTaskById (Long Id);
    SimpleTask updateSimpleTask (Long simpleTaskId , SimpleTask simpleTaskRequest);
    ResponseEntity<?> deleteSimpleTask (Long simpleTaskId);
}
