package com.speedplanner.service;

import com.speedplanner.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface NotificationsService {
    Page<Notification> getAllNotification(Pageable pageable);
    Notification createNotification (Notification notification);
    Notification getNotificationById (Long Id);
    Notification updateNotification (Long notificationsId, Notification notificationRequest);
    ResponseEntity<?> deleteNotification (Long notificationId);
}
