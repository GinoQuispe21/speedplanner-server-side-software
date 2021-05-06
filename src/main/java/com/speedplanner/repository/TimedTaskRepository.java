package com.speedplanner.repository;

import com.speedplanner.model.TimedTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimedTaskRepository extends JpaRepository<TimedTask, Long> {
    TimedTask findByIdAndStudyGroupId(Long id, Long studyGroupId);
    Page<TimedTask> findAllByStudyGroupId(Long studyGroupId, Pageable pageable);
}
