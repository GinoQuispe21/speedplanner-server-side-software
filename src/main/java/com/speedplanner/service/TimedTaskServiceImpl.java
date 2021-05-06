package com.speedplanner.service;

import com.speedplanner.exception.ResourceNotFoundException;
import com.speedplanner.model.TimedTask;
import com.speedplanner.repository.StudyGroupRepository;
import com.speedplanner.repository.TimedTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TimedTaskServiceImpl implements TimedTaskService {

    @Autowired
    private TimedTaskRepository timedTaskRepository;

    @Autowired
    private StudyGroupRepository studyGroupRepository;

    //TODO: Task and StudyGroup Logic

    @Override
    public Page<TimedTask> getAllTimedTasksByStudyGroupId(Long studyGroupId, Pageable pageable) {
        this.ValidateStudyGroup(studyGroupId);
        return timedTaskRepository.findAllByStudyGroupId(studyGroupId, pageable);
    }

    @Override
    public TimedTask getTimedTaskByIdAndStudyGroupId(Long studyGroupId, Long Id) {
        this.ValidateStudyGroup(studyGroupId);
        return timedTaskRepository.findByIdAndStudyGroupId(Id, studyGroupId);
    }

    @Override
    public TimedTask createTimedTask(Long studyGroupId, TimedTask timedTask) {
        return studyGroupRepository.findById(studyGroupId).map(studyGroup -> {
            timedTask.setStudyGroup(studyGroup);
            return timedTaskRepository.save(timedTask);
        }).orElseThrow(() -> new ResourceNotFoundException("Study Group not found with Id: "+studyGroupId));
    }

    @Override
    public TimedTask updateTimedTask(Long studyGroupId, Long timedTaskId, TimedTask timedTaskRequest) {
        return studyGroupRepository.findById(studyGroupId).map(studyGroup -> {
            TimedTask timedTask = timedTaskRepository.findById(timedTaskId)
                    .orElseThrow(() -> new ResourceNotFoundException("Timed Task not found with Id: "+timedTaskId));
            timedTask.setFinished(timedTaskRequest.isFinished());
            timedTask.setStartTime(timedTaskRequest.getStartTime());
            timedTask.setFinishTime(timedTaskRequest.getFinishTime());
            timedTask.setTitle(timedTaskRequest.getTitle());
            timedTask.setDescription(timedTaskRequest.getDescription());
            return timedTaskRepository.save(timedTask);
        }).orElseThrow(() -> new ResourceNotFoundException("Study Group not found with Id: "+studyGroupId));
    }

    @Override
    public ResponseEntity<?> deleteTimedTask(Long studyGroupId, Long timedTaskId) {
        return studyGroupRepository.findById(studyGroupId).map(studyGroup -> {
            TimedTask timedTask = timedTaskRepository.findById(timedTaskId).orElseThrow(() ->
                    new ResourceNotFoundException("Timed Task not found with Id: "+timedTaskId));
            timedTaskRepository.delete(timedTask);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Study Group not found with Id: "+studyGroupId));
    }

    //Revisar que un study group exista, evita repetición de código
    public void ValidateStudyGroup(Long studyGroupId){
        if (!studyGroupRepository.existsById(studyGroupId))
            throw new ResourceNotFoundException("StudyGroup", "Id", studyGroupId);
    }
}
