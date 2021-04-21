package com.speedplanner.repository;

import com.speedplanner.model.TimedTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimedTasksRepository extends JpaRepository<TimedTask, Long> {
}
