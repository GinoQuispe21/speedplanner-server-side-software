package com.speedplanner.service;

import com.speedplanner.model.SimpleTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface SimpleTaskService {
    Page<SimpleTask> getAllSimpleTasksByCourseId (Long courseId, Pageable pageable);
    SimpleTask createSimpleTask (Long courseId, SimpleTask simpleTask);
    SimpleTask getSimpleTaskByIdAndCourseId (Long courseId, Long Id);
    SimpleTask updateSimpleTask (Long courseId, Long simpleTaskId , SimpleTask simpleTaskRequest);
    ResponseEntity<?> deleteSimpleTask (Long courseId, Long simpleTaskId);
}
