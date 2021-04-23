package com.speedplanner.controller;

import com.speedplanner.model.Notification;
import com.speedplanner.resource.NotificationsResource;
import com.speedplanner.resource.SaveNotificationsResource;
import com.speedplanner.service.NotificationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Notifications", description = "Notification API")
@RestController
@CrossOrigin
@RequestMapping("/api")
public class NotificationController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private NotificationsService notificationsService;

    @Operation(summary = "Get all notifications", description = "Get all the notifications from Speedplanner",
            tags = { "notifications" })
    @GetMapping("/notifications")
    public Page<NotificationsResource> getAllNotifications(Pageable pageable) {
        Page<Notification> notificationPage = notificationsService.getAllNotification(pageable);
        List<NotificationsResource> resource = notificationPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resource , pageable , resource.size());
    }

    @Operation(summary = "Gets a notification",
            description = "Gets the information of a particular notification, given its Id.", tags = { "notifications" })
    @GetMapping("notifications/{id}")
    public NotificationsResource getNotificationById(@PathVariable(name = "id") Long notificationId){
        return convertToResource(notificationsService.getNotificationById(notificationId));
    }

    @Operation(summary = "Gets a notification",
            description = "Gets the information of a particular notification, given the Id of its related Simple Task.",
            tags = { "notifications", "simple tasks" })
    @GetMapping("/simpletasks/{simpleTaskId}/notifications")
    public NotificationsResource getNotificationBySimpleTaskId(@PathVariable(name = "simpleTaskId") Long simpleTaskId){
        Notification notification = notificationsService.getNotificationBySimpleTaskId(simpleTaskId);
        return convertToResource(notification);
    }

    @Operation(summary = "Create a notification", description = "Creates a new notification, related to an existing" +
            "Simple Task",
            tags = { "notifications", "simple tasks" })
    @PostMapping("/simpletasks/{simpleTaskId}/notifications")
    public NotificationsResource createNotification(@PathVariable(name = "simpleTaskId") Long simpleTaskId, @Valid @RequestBody SaveNotificationsResource resource){
        Notification notification = convertToEntity(resource);
        return convertToResource(notificationsService.createNotification(simpleTaskId,notification));
    }

    @Operation(summary = "Update a notification.",
            description = "Updates a notification related to a Simple Task", tags = { "notifications", "simple tasks" })
    @PutMapping("/simpletasks/{simpleTaskId}/notifications")
    public NotificationsResource updateNotifications(@PathVariable(name = "simpleTaskId") Long simpleTaskId, @Valid @RequestBody SaveNotificationsResource resource){
        return convertToResource(notificationsService.updateNotification(simpleTaskId, convertToEntity(resource)));
    }

    @Operation(summary = "Delete a notification.", description = "Deletes an existing notification, given its Id.",
            tags = { "notifications" })
    @DeleteMapping("notifications/{id}")
    public ResponseEntity<?> deleteNotifications(@PathVariable(name = "id") Long notificationId){
       return notificationsService.deleteNotification(notificationId);
    }

    private Notification convertToEntity(SaveNotificationsResource resource) {return mapper.map(resource , Notification.class);}

    private NotificationsResource convertToResource(Notification entity) {return mapper.map(entity , NotificationsResource.class);}
}
