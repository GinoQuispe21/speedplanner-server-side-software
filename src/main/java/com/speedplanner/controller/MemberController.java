package com.speedplanner.controller;

import com.speedplanner.model.Member;
import com.speedplanner.resource.MemberResource;
import com.speedplanner.resource.SaveGroupResource;
import com.speedplanner.resource.SaveMemberResource;
import com.speedplanner.service.MemberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class MemberController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MemberService memberService;

    @GetMapping("/groups/{groupId}/members")
    public Page<MemberResource> getAllMembersByGroupId(@PathVariable(name = "groupId") Long groupId, Pageable pageable) {
        Page<Member> memberPage = memberService.getAllMembersByGroupId(groupId, pageable);
        List<MemberResource> resources = memberPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @PostMapping("/groups/{groupId}/members")
    public MemberResource createMember(@PathVariable(name = "groupId") Long groupId, @Valid @RequestBody SaveMemberResource resource) {

        return convertToResource(memberService.createMember(groupId, convertToEntity(resource)));
    }

    @PutMapping("/groups/{groupId}/members/{memberId}")
    public MemberResource updateMember(@PathVariable(name = "groupId") Long groupId, @PathVariable(name = "memberId") Long memberId, @Valid @RequestBody SaveMemberResource resource) {
        return convertToResource(memberService.updateMember(groupId, memberId, convertToEntity(resource)));
    }

    @DeleteMapping("/groups/{groupId}/members/{memberId}")
    public ResponseEntity<?> deleteMember(@PathVariable(name = "groupId") Long groupId, @PathVariable(name = "memberId") Long memberId) {
        return memberService.deleteMember(groupId, memberId);
    }

    @GetMapping("/groups/{groupId}/members/{memberId}")
    public MemberResource getMemberByIdAndGroupId(@PathVariable(name = "groupId") Long groupId, @PathVariable(name = "memberId") Long memberId) {
        return convertToResource(memberService.getMemberByIdAndGroupId(groupId, memberId));
    }

    private Member convertToEntity(SaveMemberResource resource) { return mapper.map(resource, Member.class); }

    private MemberResource convertToResource(Member entity) { return mapper.map(entity, MemberResource.class); }
}
