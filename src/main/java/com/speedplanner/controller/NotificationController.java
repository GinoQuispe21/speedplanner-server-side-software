package com.speedplanner.controller;

import com.speedplanner.model.Notification;
import com.speedplanner.resource.NotificationResource;
import com.speedplanner.resource.SaveNotificationsResource;
import com.speedplanner.service.NotificationService;
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
    private NotificationService notificationService;

    @Operation(summary = "Get all notifications", description = "Get all the notifications from Speedplanner",
            tags = { "notifications" })
    @GetMapping("/notifications")
    public Page<NotificationResource> getAllNotifications(Pageable pageable) {
        Page<Notification> notificationPage = notificationService.getAllNotifications(pageable);
        List<NotificationResource> resource = notificationPage.getContent().stream().map(this::convertToResource).
                collect(Collectors.toList());
        return new PageImpl<>(resource , pageable , resource.size());
    }

    @Operation(summary = "Gets a notification",
            description = "Gets the information of a particular notification, given its Id and its " +
                    "corresponding Simple Task Id.", tags = { "notifications" })
    @GetMapping("/simpleTasks/{simpleTaskId}/notifications/{id}")
    public NotificationResource getNotificationByIdAndSimpleTaskId(@PathVariable(name = "id") Long notificationId,
                                                                   @PathVariable(name = "simpleTaskId") Long simpleTaskId){
        return convertToResource(notificationService.getNotificationByIdAndSimpleTaskId(notificationId, simpleTaskId));
    }

    @Operation(summary = "Gets a notification",
            description = "Gets the information of a particular notification, given its Id and its corresponding" +
                    " Timed Task Id.", tags = { "notifications", "timed tasks" })
    @GetMapping("/timedTasks/{timedTaskId}/notifications/{id}")
    public NotificationResource getNotificationByIdAndTimedTaskId(@PathVariable(name = "id") Long notificationId,
                                                                  @PathVariable(name = "timedTaskId") Long timedTaskId){
        return convertToResource(notificationService.getNotificationByIdAndTimedTaskId(notificationId, timedTaskId));
    }

    @Operation(summary = "Create a notification", description = "Creates a new notification, related to an existing" +
            "Simple Task, given its Id.",
            tags = { "notifications", "simple tasks" })
    @PostMapping("/simpleTasks/{simpleTaskId}/notifications")
    public NotificationResource createSimpleNotification(@PathVariable(name = "simpleTaskId") Long simpleTaskId,
                                                          @Valid @RequestBody SaveNotificationsResource resource){
        Notification notification = convertToEntity(resource);
        return convertToResource(notificationService.createSimpleNotification(simpleTaskId,notification));
    }

    @Operation(summary = "Create a notification", description = "Creates a new notification, related to an existing" +
            "Timed Task, given its Id.",
            tags = { "notifications", "timed tasks" })
    @PostMapping("/timedTasks/{timedTaskId}/notifications")
    public NotificationResource createTimedNotification(@PathVariable(name = "timedTaskId") Long timedTaskId,
                                                        @Valid @RequestBody SaveNotificationsResource resource){
        Notification notification = convertToEntity(resource);
        return convertToResource(notificationService.createTimedNotification(timedTaskId,notification));
    }

    @Operation(summary = "Update a notification.",
            description = "Updates a notification related to a Simple Task, given both Ids.",
            tags = { "notifications", "simple tasks" })
    @PutMapping("/simpleTasks/{simpleTaskId}/notifications/{id}")
    public NotificationResource updateSimpleNotification(@PathVariable(name = "id") Long id,
                                                         @PathVariable(name = "simpleTaskId") Long simpleTaskId,
                                                         @Valid @RequestBody SaveNotificationsResource resource){
        return convertToResource(notificationService.updateSimpleNotification(id, simpleTaskId, convertToEntity(resource)));
    }

    @Operation(summary = "Update a notification.",
            description = "Updates a notification related to a Timed Task, given both Ids.", tags = { "notifications", "timed tasks" })
    @PutMapping("/timedTasks/{timedTaskId}/notifications/{id}")
    public NotificationResource updateTimedNotification(@PathVariable(name = "id") Long id,
                                                        @PathVariable(name = "timedTaskId") Long timedTaskId,
                                                        @Valid @RequestBody SaveNotificationsResource resource){
        return convertToResource(notificationService.updateTimedNotification(id, timedTaskId, convertToEntity(resource)));
    }

    @Operation(summary = "Delete a notification.", description = "Deletes an existing notification, given its Id.",
            tags = { "notifications" })
    @DeleteMapping("notifications/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable(name = "id") Long notificationId){
       return notificationService.deleteNotification(notificationId);
    }

    private Notification convertToEntity(SaveNotificationsResource resource) {return mapper.map(resource,
            Notification.class);}

    private NotificationResource convertToResource(Notification entity) {return mapper.map(entity,
            NotificationResource.class);}
}
