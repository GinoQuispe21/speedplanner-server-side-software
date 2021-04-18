package com.speedplanner.service;

import com.speedplanner.model.Notification;
import com.speedplanner.model.SimpleTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface NotificationsService {
    Page<Notification> getAllNotificationBySimpleTaskId(Long simpleTaskId,Pageable pageable);
    Notification createNotification (Long simpleTaskId , Notification notification);
    Notification getNotificationByIdAndSimpleTaskId(Long simpleTaskId, Long Id);
    Notification updateNotification (Long simpleTaskId,Long notificationId, Notification notificationRequest);
    ResponseEntity<?> deleteNotification (Long simpleTaskId,Long notificationId);
}
