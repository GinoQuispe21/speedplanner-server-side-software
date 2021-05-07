package com.speedplanner.service;

import com.speedplanner.model.SimpleTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface SimpleTaskService {
    Page<SimpleTask> getAllSimpleTasksByStudyGroupId (Long studyGroupId, Pageable pageable);
    SimpleTask createSimpleTask (Long studyGroupId, SimpleTask simpleTask);
    SimpleTask getSimpleTaskByIdAndStudyGroupId (Long studyGroupId, Long Id);
    SimpleTask updateSimpleTask (Long studyGroupId, Long simpleTaskId , SimpleTask simpleTaskRequest);
    ResponseEntity<?> deleteSimpleTask (Long studyGroupId, Long simpleTaskId);
}
