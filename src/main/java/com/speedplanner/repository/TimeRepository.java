package com.speedplanner.repository;

import com.speedplanner.model.Course;
import com.speedplanner.model.Time;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimeRepository extends JpaRepository<Time, Long> {
    Page<Time> findByCourseId(Long courseId, Pageable pageable);
    Optional<Time> findByIdAndCourseId(Long id, Long courseId);
}
