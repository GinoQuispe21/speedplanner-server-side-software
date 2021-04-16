package com.speedplanner.controller;

import com.speedplanner.model.Group;
import com.speedplanner.resource.GroupResource;
import com.speedplanner.resource.SaveGroupResource;
import com.speedplanner.service.GroupService;
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
public class GroupController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private GroupService groupService;

    @GetMapping("/groups")
    public Page<GroupResource> getAllGroups(Pageable pageable) {
        Page<Group> groupPage = groupService.getAllGroups(pageable);
        List<GroupResource> resources = groupPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/groups/{id}")
    public GroupResource getGroupById(@PathVariable(name = "id") Long groupId) {
        return convertToResource(groupService.getGroupById(groupId));
    }

    @PostMapping("/groups")
    public GroupResource createGroup(@Valid @RequestBody SaveGroupResource resource)  {
        Group group = convertToEntity(resource);
        return convertToResource(groupService.createGroup(group));
    }

    @PutMapping("/groups/{id}")
    public GroupResource updateGroup(@PathVariable(name = "id") Long groupId, @Valid @RequestBody SaveGroupResource resource) {
        Group group = convertToEntity(resource);
        return convertToResource(groupService.updateGroup(groupId, group));
    }

    @DeleteMapping("/groups/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable(name = "id") Long groupId) {
        return groupService.deleteGroup(groupId);
    }

    private Group convertToEntity(SaveGroupResource resource) { return mapper.map(resource, Group.class); }

    private GroupResource convertToResource(Group entity) { return mapper.map(entity, GroupResource.class); }
}
