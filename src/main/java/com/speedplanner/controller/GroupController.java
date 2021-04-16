package com.speedplanner.controller;

import com.speedplanner.model.Group;
import com.speedplanner.resource.GroupResource;
import com.speedplanner.resource.SaveGroupResource;
import com.speedplanner.service.GroupService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class GroupController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private GroupService groupService;


    private Group convertToEntity(SaveGroupResource resource) { return mapper.map(resource, Group.class); }

    private GroupResource convertToResource(Group entity) { return mapper.map(entity, GroupResource.class); }
}
