package com.speedplanner.service;

import com.speedplanner.exception.ResourceNotFoundException;
import com.speedplanner.model.TimedTask;
import com.speedplanner.repository.TimedTasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TimedTasksServiceImp implements  TimedTasksService{

    @Autowired
    private TimedTasksRepository TimedTasksRepository;

    @Override
    public Page<TimedTask> getAllTimedTasks(Pageable pageable) {
        return TimedTasksRepository.findAll(pageable);
    }

    @Override
    public TimedTask createTimedTask(TimedTask TimedTask) {
        return TimedTasksRepository.save(TimedTask);
    }

    @Override
    public TimedTask getTimedTaskById(Long Id) {
        return TimedTasksRepository.findById(Id).orElseThrow(
                () -> new ResourceNotFoundException("Timed Task not found with Id " + Id ));
    }

    @Override
    public TimedTask updateTimedTask(Long TimedTaskId, TimedTask TimedTaskRequest) {
        TimedTask timedTask = TimedTasksRepository.findById(TimedTaskId).orElseThrow(
                () -> new ResourceNotFoundException("Timed Task not found with Id " + TimedTaskId ));
        timedTask.setTitle(TimedTaskRequest.getTitle());
        timedTask.setDescription(TimedTaskRequest.getDescription());
        timedTask.setFinished(TimedTaskRequest.isFinished());
        timedTask.setFinish_time(TimedTaskRequest.getFinish_time());
        timedTask.setStart_time(TimedTaskRequest.getStart_time());
        return TimedTasksRepository.save(timedTask);
    }

    @Override
    public ResponseEntity<?> deleteTimedTask(Long TimedTaskId) {
        TimedTask timedTask = TimedTasksRepository.findById(TimedTaskId).orElseThrow(() -> new ResourceNotFoundException("Timed Task" , "Id" , TimedTaskId));
        TimedTasksRepository.delete(timedTask);
        return ResponseEntity.ok().build();
    }
}
