package com.speedplanner.service;

import com.speedplanner.exception.ResourceNotFoundException;
import com.speedplanner.model.Member;
import com.speedplanner.repository.GroupRepository;
import com.speedplanner.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;


import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Page<Member> getAllMembersByGroupId(Long groupId, Pageable pageable) {
        return memberRepository.findByGroupId(groupId, pageable);
    }

    @Override
    public Member getMemberByIdAndGroupId(Long groupId, Long memberId) {
        return memberRepository.findByIdAndGroupId(memberId, groupId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Member not found with Id" + memberId +
                                "and Group Id " + groupId
                ));
    }

    @Override
    public Member createMember(Long groupId, Member member) {
        return groupRepository.findById(groupId).map(group -> {
            member.setGroup(group);
            return memberRepository.save(member);
        }).orElseThrow(() -> new ResourceNotFoundException("Group", "Id", groupId));
    }

    @Override
    public Member updateMember(Long groupId, Long memberId, Member memberDetail) {
        if(!groupRepository.existsById(groupId))
        throw new ResourceNotFoundException("Group", "Id", groupId);

        return memberRepository.findById(memberId).map(member -> {
            member.setFull_name(memberDetail.getFull_name());
            member.setDescription(memberDetail.getDescription());
            return memberRepository.save(member);
        }).orElseThrow(() -> new ResourceNotFoundException("Member", "Id", memberId));
    }

    @Override
    public ResponseEntity<?> deleteMember(Long groupId, Long memberId) {
        return memberRepository.findByIdAndGroupId(memberId, groupId).map(member ->{
            memberRepository.delete(member);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(" Member not found with Id " + memberId + " and Group Id " + groupId));
    }
}
