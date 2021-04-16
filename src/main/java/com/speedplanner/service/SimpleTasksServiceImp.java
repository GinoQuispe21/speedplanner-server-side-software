package com.speedplanner.service;

import com.speedplanner.exception.ResourceNotFoundException;
import com.speedplanner.model.SimpleTask;
import com.speedplanner.repository.SimpleTasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public class SimpleTasksServiceImp implements  SimpleTasksService{

    @Autowired
    private SimpleTasksRepository simpleTasksRepository;

    @Override
    public Page<SimpleTask> getAllSimpleTasks(Pageable pageable) {
        return simpleTasksRepository.findAll(pageable);
    }

    @Override
    public SimpleTask createSimpleTask(SimpleTask simpleTask) {
        return simpleTasksRepository.save(simpleTask);
    }

    @Override
    public SimpleTask getSimpleTaskById(Long Id) {
        return simpleTasksRepository.findById(Id).orElseThrow(
                () -> new ResourceNotFoundException("Simple Task not found with Id " + Id ));
    }

    @Override
    public SimpleTask updateSimpleTask(Long simpleTaskId, SimpleTask simpleTaskRequest) {
        SimpleTask simpleTask = simpleTasksRepository.findById(simpleTaskId).orElseThrow(
                () -> new ResourceNotFoundException("Simple Task not found with Id " + simpleTaskId ));
        simpleTask.setTitle(simpleTaskRequest.getTitle());
        simpleTask.setDescription(simpleTaskRequest.getDescription());
        simpleTask.setFinished(simpleTaskRequest.isFinished());
        simpleTask.setDeadline(simpleTaskRequest.getDeadline());
        return simpleTasksRepository.save(simpleTask);
    }

    @Override
    public ResponseEntity<?> deleteSimpleTask(Long simpleTaskId) {
        SimpleTask simpleTask = simpleTasksRepository.findById(simpleTaskId).orElseThrow(() -> new ResourceNotFoundException("Simple Task" , "Id" , simpleTaskId));
        simpleTasksRepository.delete(simpleTask);
        return ResponseEntity.ok().build();
    }
}
