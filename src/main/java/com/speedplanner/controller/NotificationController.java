package com.speedplanner.controller;

import com.speedplanner.model.Notification;
import com.speedplanner.resource.NotificationsResource;
import com.speedplanner.resource.SaveNotificationsResource;
import com.speedplanner.service.NotificationsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class NotificationController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private NotificationsService notificationsService;

    @GetMapping("/notifications")
    public Page<NotificationsResource> getAllNotifications(Pageable pageable) {
        Page<Notification> notificationPage = notificationsService.getAllNotification(pageable);
        List<NotificationsResource> resource = notificationPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resource , pageable , resource.size());
    }

    @GetMapping("/notifications/{id}")
    public NotificationsResource getNotificationById(@PathVariable(name = "id") Long notificationId){
        return convertToResource(notificationsService.getNotificationById(notificationId));
    }

    @PostMapping("/notifications")
    public NotificationsResource createNotification(@Valid @RequestBody SaveNotificationsResource resource){
        Notification notification = convertToEntity(resource);
        return convertToResource(notificationsService.createNotification(notification));
    }

    @PutMapping("/notifications/{id}")
    public NotificationsResource updateNotifications(@PathVariable(name = "id") Long notificationId , @Valid @RequestBody SaveNotificationsResource resource){
        Notification notification = convertToEntity(resource);
        return convertToResource(notificationsService.updateNotification(notificationId , notification ));
    }

    @DeleteMapping("/notifications/{id}")
    public ResponseEntity<?> deleteNotifications(@PathVariable(name = "id") Long notificationId){
        return notificationsService.deleteNotification(notificationId);
    }

    private Notification convertToEntity(SaveNotificationsResource resource) {return mapper.map(resource , Notification.class);}

    private NotificationsResource convertToResource(Notification entity) {return mapper.map(entity , NotificationsResource.class);}
}
