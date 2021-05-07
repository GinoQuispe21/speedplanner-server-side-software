package com.speedplanner.service;

import com.speedplanner.exception.ResourceNotFoundException;
import com.speedplanner.model.Member;
import com.speedplanner.repository.StudyGroupRepository;
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
    private StudyGroupRepository studyGroupRepository;

    @Override
    public Page<Member> getAllMembersByStudyGroupId(Long studyGroupId, Pageable pageable) {
        return memberRepository.findByStudyGroupId(studyGroupId, pageable);
    }

    @Override
    public Member getMemberByIdAndStudyGroupId(Long groupId, Long memberId) {
        return memberRepository.findByIdAndStudyGroupId(memberId, groupId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Member not found with Id" + memberId +
                                "and Group Id " + groupId
                ));
    }

    @Override
    public Member createMember(Long groupId, Member member) {
        return studyGroupRepository.findById(groupId).map(studyGroup -> {
            member.setStudyGroup(studyGroup);
            return memberRepository.save(member);
        }).orElseThrow(() -> new ResourceNotFoundException("Group", "Id", groupId));
    }

    @Override
    public Member updateMember(Long groupId, Long memberId, Member memberDetail) {
        if(!studyGroupRepository.existsById(groupId))
        throw new ResourceNotFoundException("Group", "Id", groupId);

        return memberRepository.findById(memberId).map(member -> {
            member.setFullName(memberDetail.getFullName());
            member.setDescription(memberDetail.getDescription());
            return memberRepository.save(member);
        }).orElseThrow(() -> new ResourceNotFoundException("Member", "Id", memberId));
    }

    @Override
    public ResponseEntity<?> deleteMember(Long groupId, Long memberId) {
        return memberRepository.findByIdAndStudyGroupId(memberId, groupId).map(member ->{
            memberRepository.delete(member);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(" Member not found with Id " + memberId + " and Group Id " + groupId));
    }
}
