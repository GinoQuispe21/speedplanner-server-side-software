package com.speedplanner.service;

import com.speedplanner.model.Notification;
import com.speedplanner.repository.NotificationsRepository;
import com.speedplanner.repository.SimpleTasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import com.speedplanner.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class NotificationsServiceImpl implements NotificationsService {
    @Autowired
    private SimpleTasksRepository simpleTasksRepository;

    @Autowired
    private NotificationsRepository notificationsRepository;

    @Override
    public Notification createNotification(Long simpleTaskId, Notification notification) {
        return simpleTasksRepository.findById(simpleTaskId).map(SimpleTask -> {
            notification.setSimpleTask(SimpleTask);
            return notificationsRepository.save(notification);
        }).orElseThrow(
                () -> new ResourceNotFoundException("Simple Task not found with id: " + simpleTaskId));
    }

    @Override
    public Notification getNotificationBySimpleTaskId(Long simpleTaskId) {
        return notificationsRepository.findById(simpleTaskId).orElseThrow(
                () -> new ResourceNotFoundException("Simple Task not found with Id " + simpleTaskId ));
    }

    @Override
    public Notification getNotificationById(Long notificationsId){
        return notificationsRepository.findById(notificationsId).orElseThrow(
                () -> new ResourceNotFoundException("Notification not found with Id: " + notificationsId ));
    }


    @Override
    public Page<Notification> getAllNotification(Pageable pageable) {
        return notificationsRepository.findAll(pageable);
    }



    @Override
    public Notification updateNotification(Long simpleTaskId,  Notification notificationRequest) {
        if (!simpleTasksRepository.existsById(simpleTaskId))
            throw new ResourceNotFoundException("Simple Task", "Id", simpleTaskId);
        Notification notification = notificationsRepository.findBySimpleTaskId(simpleTaskId);
        notification.setMessage(notificationRequest.getMessage());
        notification.setReminder_date(notificationRequest.getReminder_date());
        return notificationsRepository.save(notification);
    }

    @Override
    public ResponseEntity<?> deleteNotification(Long notificationId) {
        return notificationsRepository.findById(notificationId).map(notification -> {
            notificationsRepository.delete(notification);
            return ResponseEntity.ok().build();
        }).orElseThrow(
                () -> new ResourceNotFoundException("Notification not found with Id " + notificationId ));
    }
}


