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
                () -> new ResourceNotFoundException("Simple Task", "Id", simpleTaskId));
    }

    @Override
    public Notification getNotificationByIdAndSimpleTaskId(Long simpleTaskId, Long Id) {
        return notificationsRepository.findByIdAndSimpleTaskId(simpleTaskId, Id).orElseThrow(
                () -> new ResourceNotFoundException("Notification not found with Id " + Id + "and Simple Task Id: " + simpleTaskId));
    }

    @Override
    public Page<Notification> getAllNotificationBySimpleTaskId(Long simpleTaskId, Pageable pageable) {
        return notificationsRepository.findBySimpleTaskId(simpleTaskId, pageable);
    }

    @Override
    public Notification updateNotification(Long simpleTaskId, Long notificationId, Notification notificationRequest) {
        if (!simpleTasksRepository.existsById(simpleTaskId))
            throw new ResourceNotFoundException("Simple Task", "Id", simpleTaskId);
        return notificationsRepository.findById(notificationId).map(notification -> {
            notification.setMessage(notificationRequest.getMessage());
            notification.setReminder_date(notificationRequest.getReminder_date());
            return notificationsRepository.save(notification);
        }).orElseThrow(() -> new ResourceNotFoundException("Notification", "Id", notificationId));
    }

    @Override
    public ResponseEntity<?> deleteNotification(Long simpleTaskId, Long notificationId) {
        return notificationsRepository.findByIdAndSimpleTaskId(simpleTaskId, notificationId).map(notification -> {
            notificationsRepository.delete(notification);
            return ResponseEntity.ok().build();
        }).orElseThrow(
                () -> new ResourceNotFoundException("Notification not found with Id " + notificationId + "and Simple Task Id: " + simpleTaskId));
    }
}


