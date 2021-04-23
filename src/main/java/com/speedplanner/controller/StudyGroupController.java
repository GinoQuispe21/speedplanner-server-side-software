package com.speedplanner.controller;

import com.speedplanner.model.StudyGroup;
import com.speedplanner.resource.GroupResource;
import com.speedplanner.resource.SaveGroupResource;
import com.speedplanner.service.StudyGroupService;
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
@Tag(name = "Study Groups", description = "Study Groups API")
public class StudyGroupController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private StudyGroupService studyGroupService;

    @Operation(summary = "Get all Study Groups", description = "Gets all the Study Groups from Speedplanner",
            tags = { "study groups" })
    @GetMapping("/studyGroups")
    public Page<GroupResource> getAllGroups(Pageable pageable) {
        Page<StudyGroup> groupPage = studyGroupService.getAllStudyGroups(pageable);
        List<GroupResource> resources = groupPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Get a Study Group by Id.", description = "Gets the information of a particular Study Group, " +
            "given its Id.",
            tags = { "study groups" })
    @GetMapping("/studyGroups/{id}")
    public GroupResource getGroupById(@PathVariable(name = "id") Long studyGroupId) {
        return convertToResource(studyGroupService.getStudyGroupById(studyGroupId));
    }

    @Operation(summary = "Create a new Study Group", description = "Creates a new Study Group", tags = { "study groups" })
    @PostMapping("/studyGroups")
    public GroupResource createGroup(@Valid @RequestBody SaveGroupResource resource)  {
        StudyGroup studyGroup = convertToEntity(resource);
        return convertToResource(studyGroupService.createStudyGroup(studyGroup));
    }

    @Operation(summary = "Update a Study Group", description = "Updates a particular Study Group, given its Id.",
            tags = { "study groups" })
    @PutMapping("/studyGroups/{id}")
    public GroupResource updateGroup(@PathVariable(name = "id") Long studyGroupId, @Valid @RequestBody SaveGroupResource resource) {
        StudyGroup studyGroup = convertToEntity(resource);
        return convertToResource(studyGroupService.updateStudyGroup(studyGroupId, studyGroup));
    }

    @Operation(summary = "Delete a Study Group", description = "Deletes a Study Group, given its Id.",
            tags = { "study groups" })
    @DeleteMapping("/studyGroups/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable(name = "id") Long studyGroupId) {
        return studyGroupService.deleteStudyGroup(studyGroupId);
    }

    private StudyGroup convertToEntity(SaveGroupResource resource) { return mapper.map(resource, StudyGroup.class); }

    private GroupResource convertToResource(StudyGroup entity) { return mapper.map(entity, GroupResource.class); }
}
