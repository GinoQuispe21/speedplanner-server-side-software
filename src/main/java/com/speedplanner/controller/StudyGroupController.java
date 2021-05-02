package com.speedplanner.controller;

import com.speedplanner.model.StudyGroup;
import com.speedplanner.resource.StudyGroupResource;
import com.speedplanner.resource.SaveStudyGroupResource;
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
//TODO: Update doc.
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
    public Page<StudyGroupResource> getAllStudyGroups(Pageable pageable) {
        Page<StudyGroup> groupPage = studyGroupService.getAllStudyGroups(pageable);
        List<StudyGroupResource> resources = groupPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Get a Study Group by Id and Course Id.", description = "Gets the information of a particular" +
            " Study Group, given its Id and the related Course Id." +
            "given its Id.",
            tags = { "study groups" })
    @GetMapping("/courses/{courseId}/studyGroups/{id}")
    public StudyGroupResource getStudyGroupByIdAndCourseId(@PathVariable(name = "id") Long studyGroupId,
                                                           @PathVariable(name = "courseId") Long courseId) {
        return convertToResource(studyGroupService.getStudyGroupByIdAndCourseId(studyGroupId, courseId));
    }

    @Operation(summary = "Create a new Study Group", description = "Creates a new Study Group", tags = { "study groups" })
    @PostMapping("/courses/{courseId}/studyGroups")
    public StudyGroupResource createStudyGroup(@Valid @RequestBody SaveStudyGroupResource resource,
                                               @PathVariable(name = "courseId") Long courseId)  {
        StudyGroup studyGroup = convertToEntity(resource);
        return convertToResource(studyGroupService.createStudyGroup(courseId, studyGroup));
    }

    @Operation(summary = "Update a Study Group", description = "Updates a particular Study Group, given its Id.",
            tags = { "study groups" })
    @PutMapping("/courses/{courseId}/studyGroups/{id}")
    public StudyGroupResource updateStudyGroup(@PathVariable(name = "id") Long studyGroupId,
                                               @PathVariable(name = "courseId") Long courseId,
                                               @Valid @RequestBody SaveStudyGroupResource resource) {
        StudyGroup studyGroup = convertToEntity(resource);
        return convertToResource(studyGroupService.updateStudyGroup(courseId, studyGroupId, studyGroup));
    }

    @Operation(summary = "Delete a Study Group", description = "Deletes a Study Group, given its Id.",
            tags = { "study groups" })
    @DeleteMapping("/courses/{courseId}/studyGroups/{id}")
    public ResponseEntity<?> deleteStudyGroup(@PathVariable(name = "id") Long studyGroupId,
                                              @PathVariable(name = "courseId") Long courseId) {
        return studyGroupService.deleteStudyGroup(courseId, studyGroupId);
    }

    private StudyGroup convertToEntity(SaveStudyGroupResource resource) { return mapper.map(resource, StudyGroup.class); }

    private StudyGroupResource convertToResource(StudyGroup entity) { return mapper.map(entity, StudyGroupResource.class); }
}
