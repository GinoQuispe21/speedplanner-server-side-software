package com.speedplanner.repository;

import com.speedplanner.model.SimpleTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleTasksRepository extends JpaRepository<SimpleTask, Long> {
}
