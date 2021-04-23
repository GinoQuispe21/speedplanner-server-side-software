package com.speedplanner.controller;

import com.speedplanner.model.Member;
import com.speedplanner.resource.MemberResource;
import com.speedplanner.resource.SaveMemberResource;
import com.speedplanner.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Members", description = "User API")
public class MemberController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MemberService memberService;

    @Operation(summary = "Get all Members by Study Group Id", description = "Get all members from a study group by the group Id", tags = { "members" })
    @GetMapping("/studyGrou ps/{studyGroupId}/members")
    public Page<MemberResource> getAllMembersByStudyGroupId(@PathVariable(name = "studyGroupId") Long studyGroupId, Pageable pageable) {
        Page<Member> memberPage = memberService.getAllMembersByStudyGroupId(studyGroupId, pageable);
        List<MemberResource> resources = memberPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Create Member by Study Group Id", description = "Create a new member from the study group by the group Id", tags = { "members" })
    @PostMapping("/studyGroups/{studyGroupId}/members")
    public MemberResource createMember(@PathVariable(name = "studyGroupId") Long studyGroupId, @Valid @RequestBody SaveMemberResource resource) {
        return convertToResource(memberService.createMember(studyGroupId, convertToEntity(resource)));
    }

    @Operation(summary = "Update Member by Study Group Id and Member Id", description = "Update a member from the study group by the group Id and member Id", tags = { "members" })
    @PutMapping("/studyGroups/{studyGroupId}/members/{memberId}")
    public MemberResource updateMember(@PathVariable(name = "studyGroupId") Long groupId, @PathVariable(name = "memberId") Long memberId,
                                       @Valid @RequestBody SaveMemberResource resource) {
        return convertToResource(memberService.updateMember(groupId, memberId, convertToEntity(resource)));
    }

    @Operation(summary = "Delete Member by Study Group Id and Member Id", description = "Delete a member from the study group by the group Id and member Id", tags = { "members" })
    @DeleteMapping("/studyGroups/{studyGroupId}/members/{memberId}")
    public ResponseEntity<?> deleteMember(@PathVariable(name = "studyGroupId") Long studyGroupId, @PathVariable(name = "memberId") Long memberId) {
        return memberService.deleteMember(studyGroupId, memberId);
    }

    @Operation(summary = "Get Member by Study Group Id and Member Id", description = "Get a member from the study group by the group Id and member Id", tags = { "members" })
    @GetMapping("/studyGroups/{studyGroupId}/members/{memberId}")
    public MemberResource getMemberByIdAndGroupId(@PathVariable(name = "studyGroupId") Long studyGroupId,
                                                  @PathVariable(name = "memberId") Long memberId) {
        return convertToResource(memberService.getMemberByIdAndStudyGroupId(studyGroupId, memberId));
    }

    private Member convertToEntity(SaveMemberResource resource) { return mapper.map(resource, Member.class); }

    private MemberResource convertToResource(Member entity) { return mapper.map(entity, MemberResource.class); }
}
