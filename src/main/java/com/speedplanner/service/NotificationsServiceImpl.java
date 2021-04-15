package com.speedplanner.service;

import com.speedplanner.model.Notification;
import com.speedplanner.repository.NotificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import com.speedplanner.exception.ResourceNotFoundException;


public class NotificationsServiceImpl implements NotificationsService{

    @Autowired
    private NotificationsRepository notificationsRepository;

    @Override
    public Notification createNotification (Notification notification){
        return notificationsRepository.save(notification);
    }

    @Override
    public Notification getNotificationById (Long Id){
        return  notificationsRepository.findById(Id).orElseThrow(
                () -> new ResourceNotFoundException("Notification not found with Id " + Id ));
    }

    @Override
    public Page<Notification> getAllNotification(Pageable pageable){
        return notificationsRepository.findAll(pageable);
    }

    @Override
    public Notification updateNotification (Long notificationsId, Notification notificationRequest){
        Notification notification = notificationsRepository.findById(notificationsId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with Id:" + notificationsId));
        notification.setMessage(notificationRequest.getMessage());
        notification.setReminder_date(notificationRequest.getReminder_date());
        return notificationsRepository.save(notificationRequest);
    }

    @Override
    public ResponseEntity<?> deleteNotification (Long notificationId){
        return notificationsRepository.findById(notificationId).map( notification -> {
            notificationsRepository.delete(notification);
            return ResponseEntity.ok().build();
                }).orElseThrow(
                () -> new ResourceNotFoundException("Notification not found with Id " + notificationId ));
    }
}
