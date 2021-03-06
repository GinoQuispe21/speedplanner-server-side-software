package com.speedplanner.repository;

import com.speedplanner.model.SimpleTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleTaskRepository extends JpaRepository<SimpleTask, Long> {
    SimpleTask findByIdAndStudyGroupId(Long id, Long studyGroupId);
    Page<SimpleTask> findAllByStudyGroupId(Long studyGroupId, Pageable pageable);
}
