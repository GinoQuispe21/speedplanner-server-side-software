package com.speedplanner.service;

import com.speedplanner.model.Notification;
import com.speedplanner.repository.NotificationRepository;
import com.speedplanner.repository.SimpleTaskRepository;
import com.speedplanner.repository.TimedTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import com.speedplanner.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private SimpleTaskRepository simpleTaskRepository;

    @Autowired
    private TimedTaskRepository timedTaskRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification createSimpleNotification(Long simpleTaskId, Notification notification) {
        return simpleTaskRepository.findById(simpleTaskId).map(simpleTask -> {
            notification.setSimpleTask(simpleTask);
            simpleTask.setNotification(notification);
            return notificationRepository.save(notification);
        }).orElseThrow(
                () -> new ResourceNotFoundException("Simple Task not found with id: " + simpleTaskId));
    }

    public Notification createTimedNotification(Long timedTaskId, Notification notification){
        return timedTaskRepository.findById(timedTaskId).map(timedTask -> {
            notification.setTimedTask(timedTask);
            timedTask.setNotification(notification);
            return notificationRepository.save(notification);
        }).orElseThrow(
                () -> new ResourceNotFoundException("Timed Task not found with Id: " + timedTaskId));
    }

    @Override
    public Notification getNotificationByIdAndSimpleTaskId(Long Id, Long simpleTaskId) {
        return notificationRepository.findByIdAndSimpleTaskId(Id, simpleTaskId).orElseThrow(
                () -> new ResourceNotFoundException("Notification not found with Id: " + Id + "for " +
                        "Simple Task with Id: " + simpleTaskId));
    }

    @Override
    public Notification getNotificationByIdAndTimedTaskId(Long Id, Long timedTaskId) {
        return notificationRepository.findByIdAndTimedTaskId(Id, timedTaskId).orElseThrow(
                () -> new ResourceNotFoundException("Notification not found with Id: " + Id + "for " +
                        "Timed Task with Id: " + timedTaskId));
    }

    @Override
    public Page<Notification> getAllNotifications(Pageable pageable) {
        return notificationRepository.findAll(pageable);
    }

    @Override
    public Notification updateSimpleNotification(Long Id, Long simpleTaskId, Notification notificationRequest) {
        if (!simpleTaskRepository.existsById(simpleTaskId))
            throw new ResourceNotFoundException("Simple Task", "Id", simpleTaskId);
        Notification notification = notificationRepository.findByIdAndSimpleTaskId(Id, simpleTaskId).
                orElseThrow(() -> new ResourceNotFoundException("Notification not found with Id: " + Id + "for " +
                        "Simple Task with Id: " + simpleTaskId));
        notification.setMessage(notificationRequest.getMessage());
        notification.setReminder_date(notificationRequest.getReminder_date());
        return notificationRepository.save(notification);
    }

    @Override
    public Notification updateTimedNotification(Long Id, Long timedTaskId, Notification notificationRequest) {
        if (!timedTaskRepository.existsById(timedTaskId))
            throw new ResourceNotFoundException("Timed Task", "Id", timedTaskId);
        Notification notification = notificationRepository.findByIdAndTimedTaskId(Id, timedTaskId).orElseThrow(() ->
                new ResourceNotFoundException("Notification not found with Id: " + Id + "for Timed Task with Id: " +
                        timedTaskId));
        notification.setMessage(notificationRequest.getMessage());
        notification.setReminder_date(notificationRequest.getReminder_date());
        return notificationRepository.save(notification);
    }

    @Override
    public ResponseEntity<?> deleteNotification(Long notificationId) {
        return notificationRepository.findById(notificationId).map(notification -> {
            notificationRepository.delete(notification);
            return ResponseEntity.ok().build();
        }).orElseThrow(
                () -> new ResourceNotFoundException("Notification not found with Id " + notificationId ));
    }
}
