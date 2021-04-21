package com.speedplanner.service;

import com.speedplanner.model.Notification;
import com.speedplanner.model.SimpleTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface NotificationsService {
    ResponseEntity<?> deleteNotification (Long notificationId);
    Notification updateNotification (Long simpleTaskId, Notification notificationRequest);
    Notification createNotification (Long simpleTaskId , Notification notification);
    Notification getNotificationById(Long notificationsId);
    Notification getNotificationBySimpleTaskId(Long simpleTaskId);
    Page<Notification> getAllNotification(Pageable pageable);
}
