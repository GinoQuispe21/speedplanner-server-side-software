package com.speedplanner.repository;
import com.speedplanner.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findByIdAndSimpleTaskId(Long Id, Long simpleTaskId);
    Optional<Notification> findByIdAndTimedTaskId(Long Id, Long timedTaskId);
}
