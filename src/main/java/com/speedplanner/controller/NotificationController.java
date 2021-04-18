package com.speedplanner.controller;

import com.speedplanner.model.Notification;
import com.speedplanner.repository.SimpleTasksRepository;
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

    @GetMapping("/simpletasks/{simpleTaskId}/notifications")
    public Page<NotificationsResource> getAllNotificationsBySimpleTaskId(@PathVariable(name = "simpleTaskId") Long simpleTaskId, Pageable pageable) {
        Page<Notification> notificationPage = notificationsService.getAllNotificationBySimpleTaskId(simpleTaskId , pageable);
        List<NotificationsResource> resource = notificationPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resource , pageable , resource.size());
    }

    @GetMapping("/simpletasks/{simpleTaskId}/notifications/{id}")
    public NotificationsResource getNotificationByIdAndSimpleTaskId(@PathVariable(name = "simpleTaskId") Long simpleTaskId , @PathVariable(name = "id") Long notificationId){
        return convertToResource(notificationsService.getNotificationByIdAndSimpleTaskId(simpleTaskId , notificationId));
    }

    @PostMapping("/simpletasks/{simpleTaskId}/notifications")
    public NotificationsResource createNotification(@PathVariable(name = "simpleTaskId") Long simpleTaskId, @Valid @RequestBody SaveNotificationsResource resource){
        Notification notification = convertToEntity(resource);
        return convertToResource(notificationsService.createNotification(simpleTaskId,notification));
    }

    @PutMapping("/simpletasks/{simpleTaskId}/notifications/{id}")
    public NotificationsResource updateNotifications(@PathVariable(name = "simpleTaskId") Long simpleTaskId ,@PathVariable(name = "id") Long notificationId , @Valid @RequestBody SaveNotificationsResource resource){
        Notification notification = convertToEntity(resource);
        return convertToResource(notificationsService.updateNotification(simpleTaskId,notificationId , notification ));
    }

    @DeleteMapping("/simpletasks/{simpleTaskId}/notifications/{id}")
    public ResponseEntity<?> deleteNotifications(@PathVariable(name = "simpleTaskId") Long simpleTaskId,@PathVariable(name = "id") Long notificationId){
       return notificationsService.deleteNotification(simpleTaskId, notificationId);
    }

    private Notification convertToEntity(SaveNotificationsResource resource) {return mapper.map(resource , Notification.class);}

    private NotificationsResource convertToResource(Notification entity) {return mapper.map(entity , NotificationsResource.class);}
}
