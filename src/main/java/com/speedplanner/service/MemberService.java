package com.speedplanner.service;

import com.speedplanner.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface MemberService {
    Page<Member> getAllMembersByGroupId(Long groupId, Pageable pageable);
    Member getMemberByIdAndGroupId(Long groupId, Long memberId);
    Member createCustomer(Long groupId, Member member);
    Member updateMember(Long groupId, Long memberId, Member memberDetail);
    ResponseEntity<?> deleteMember(Long groupId, Long memberId);
}
