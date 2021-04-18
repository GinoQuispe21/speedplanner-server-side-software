package com.speedplanner.repository;

import com.speedplanner.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationsRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findBySimpleTaskId(Long simpleTaskId , Pageable pageable);
    Optional<Notification> findByIdAndSimpleTaskId(Long id , Long simpleTaskId);
}
