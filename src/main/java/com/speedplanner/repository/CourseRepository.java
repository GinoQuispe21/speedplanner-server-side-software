package com.speedplanner.repository;

import com.speedplanner.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findByUserId(Long userId, Pageable pageable);
    Optional<Course> findByIdAndUserId(Long id, Long userId);
}
