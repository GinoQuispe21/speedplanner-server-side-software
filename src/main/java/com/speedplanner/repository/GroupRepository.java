package com.speedplanner.repository;

import com.speedplanner.model.Group;
import com.speedplanner.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    /*Group findByGroupName(String groupName);*/
}
