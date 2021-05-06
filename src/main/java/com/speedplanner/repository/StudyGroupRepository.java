package com.speedplanner.repository;

import com.speedplanner.model.StudyGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {
    Page<StudyGroup> findAllByCourseId(Pageable pageable, Long courseId);
    StudyGroup findByIdAndCourseId(Long studyGroupId, Long courseId);
}
