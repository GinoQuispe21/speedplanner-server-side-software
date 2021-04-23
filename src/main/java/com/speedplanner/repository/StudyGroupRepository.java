package com.speedplanner.repository;

import com.speedplanner.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {
    StudyGroup findByName(String studyGroupName);
}
