package com.speedplanner.service;

import com.speedplanner.exception.ResourceNotFoundException;
import com.speedplanner.model.SimpleTask;
import com.speedplanner.repository.StudyGroupRepository;
import com.speedplanner.repository.SimpleTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SimpleTaskServiceImpl implements SimpleTaskService {

    @Autowired
    private SimpleTaskRepository simpleTaskRepository;

    @Autowired
    private StudyGroupRepository studyGroupRepository;

    @Override
    public Page<SimpleTask> getAllSimpleTasksByStudyGroupId(Long studyGroupId, Pageable pageable) {
        this.ValidateStudyGroup(studyGroupId);
        return simpleTaskRepository.findAllByStudyGroupId(studyGroupId, pageable);
    }

    @Override
    public SimpleTask getSimpleTaskByIdAndStudyGroupId(Long studyGroupId, Long Id) {
        this.ValidateStudyGroup(studyGroupId);
        return simpleTaskRepository.findByIdAndStudyGroupId(Id, studyGroupId);
    }

    @Override
    public SimpleTask createSimpleTask(Long studyGroupId, SimpleTask simpleTask) {
        return studyGroupRepository.findById(studyGroupId).map(studyGroup -> {
            simpleTask.setStudyGroup(studyGroup);
            return simpleTaskRepository.save(simpleTask);
        }).orElseThrow(() -> new ResourceNotFoundException("StudyGroup not found with Id: "+studyGroupId));
    }

    @Override
    public SimpleTask updateSimpleTask(Long studyGroupId, Long simpleTaskId, SimpleTask simpleTaskRequest) {
        return studyGroupRepository.findById(studyGroupId).map(studyGroup ->{
            SimpleTask simpleTask = simpleTaskRepository.findById(simpleTaskId)
                    .orElseThrow(() -> new ResourceNotFoundException("Simple Task", "Id", simpleTaskId));
            simpleTask.setFinished(simpleTaskRequest.isFinished());
            simpleTask.setDeadline(simpleTaskRequest.getDeadline());
            simpleTask.setTitle(simpleTaskRequest.getTitle());
            simpleTask.setDescription(simpleTaskRequest.getDescription());
            return simpleTaskRepository.save(simpleTask);
        }).orElseThrow(() -> new ResourceNotFoundException("StudyGroup not found with Id: "+studyGroupId));
    }

    @Override
    public ResponseEntity<?> deleteSimpleTask(Long studyGroupId, Long simpleTaskId) {
        return studyGroupRepository.findById(studyGroupId).map(studyGroup -> {
            SimpleTask simpleTask = simpleTaskRepository.findById(simpleTaskId).orElseThrow(() ->
                    new ResourceNotFoundException("Simple Task", "Id", simpleTaskId));
            simpleTaskRepository.delete(simpleTask);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("StudyGroup not found with Id: "+studyGroupId));
    }

    //Revisar que un study group exista, evita repetición de código
    public void ValidateStudyGroup(Long studyGroupId){
        if (!studyGroupRepository.existsById(studyGroupId))
            throw new ResourceNotFoundException("StudyGroup", "Id", studyGroupId);
    }


}
