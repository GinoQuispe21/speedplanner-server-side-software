package com.speedplanner.service;

import com.speedplanner.exception.ResourceNotFoundException;
import com.speedplanner.model.SimpleTask;
import com.speedplanner.repository.SimpleTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SimpleTaskServiceImp implements SimpleTaskService {

    @Autowired
    private SimpleTaskRepository simpleTaskRepository;

    @Override
    public Page<SimpleTask> getAllSimpleTasks(Pageable pageable) {
        return simpleTaskRepository.findAll(pageable);
    }

    @Override
    public SimpleTask createSimpleTask(SimpleTask simpleTask) {
        return simpleTaskRepository.save(simpleTask);
    }

    @Override
    public SimpleTask getSimpleTaskById(Long Id) {
        return simpleTaskRepository.findById(Id).orElseThrow(
                () -> new ResourceNotFoundException("Simple Task not found with Id " + Id ));
    }

    @Override
    public SimpleTask updateSimpleTask(Long simpleTaskId, SimpleTask simpleTaskRequest) {
        SimpleTask simpleTask = simpleTaskRepository.findById(simpleTaskId).orElseThrow(
                () -> new ResourceNotFoundException("Simple Task not found with Id " + simpleTaskId ));
        simpleTask.setTitle(simpleTaskRequest.getTitle());
        simpleTask.setDescription(simpleTaskRequest.getDescription());
        simpleTask.setFinished(simpleTaskRequest.isFinished());
        simpleTask.setDeadline(simpleTaskRequest.getDeadline());
        return simpleTaskRepository.save(simpleTask);
    }

    @Override
    public ResponseEntity<?> deleteSimpleTask(Long simpleTaskId) {
        SimpleTask simpleTask = simpleTaskRepository.findById(simpleTaskId).orElseThrow(() -> new ResourceNotFoundException("Simple Task" , "Id" , simpleTaskId));
        simpleTaskRepository.delete(simpleTask);
        return ResponseEntity.ok().build();
    }
}
