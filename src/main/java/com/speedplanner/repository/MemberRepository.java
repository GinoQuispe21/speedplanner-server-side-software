package com.speedplanner.repository;

import com.speedplanner.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Page<Member> findByGroupId(Long groupId, Pageable pageable);
    Optional<Member> findByIdAndGroupId(Long Id, Long groupId);
}

