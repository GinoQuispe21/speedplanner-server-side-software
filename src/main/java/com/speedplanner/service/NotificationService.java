package com.speedplanner.service;

import com.speedplanner.model.Notification;
import com.speedplanner.model.SimpleTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface NotificationService {
    ResponseEntity<?> deleteNotification (Long notificationId);
    Notification updateSimpleNotification (Long Id, Long simpleTaskId, Notification notificationRequest);
    Notification updateTimedNotification (Long Id, Long timedTaskId, Notification notificationRequest);
    Notification createSimpleNotification (Long simpleTaskId , Notification notification);
    Notification createTimedNotification (Long timedTaskId , Notification notification);
    Notification getNotificationByIdAndSimpleTaskId(Long notificationId, Long simpleTaskId);
    Notification getNotificationByIdAndTimedTaskId(Long notificationId, Long timedTaskId);
    Page<Notification> getAllNotifications(Pageable pageable);
}
