package com.speedplanner.controller;

import com.speedplanner.model.Member;
import com.speedplanner.resource.MemberResource;
import com.speedplanner.resource.SaveGroupResource;
import com.speedplanner.service.MemberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class MemberController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MemberService memberService;


    private Member convertToEntity(SaveGroupResource resource) { return mapper.map(resource, Member.class); }

    private MemberResource convertToResource(Member entity) { return mapper.map(entity, MemberResource.class); }
}
