package com.speedplanner.controller;

import com.speedplanner.model.User;
import com.speedplanner.resource.SaveUserFieldsResource;
import com.speedplanner.resource.SaveUserResource;
import com.speedplanner.resource.UserResource;
import com.speedplanner.service.UserService;
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

@CrossOrigin
@RestController
@RequestMapping("/api")
@Tag(name = "Users", description = "User API")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    ModelMapper mapper;

    @Operation(summary = "Create User", description = "Create a new user from Speedplanner", tags = { "users" })
    @PostMapping("/users")
    public UserResource createUser(@Valid @RequestBody SaveUserResource resource){
        User user = convertToEntity(resource);
        return convertToResource(userService.createUser(user));
    }

    @GetMapping("/users")
    @Operation(summary = "Get all Users", description = "Get all Users from Speedplanner", tags = { "users" })
    public Page<UserResource> getAllUsers(Pageable pageable){
        Page<User> userPage = userService.getAllUsers(pageable);
        List<UserResource> resources = userPage.getContent()
                .stream().map(this::convertToResource)
                .collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "Get User by Id", description = "Get User by Id from Speedplanner", tags = { "users" })
    public UserResource getUserById(@PathVariable(name = "userId") Long userId){
        return convertToResource(userService.getUserById(userId));
    }


    @Operation(summary = "Update User", description = "Update user by his Id from Speedplanner", tags = { "users" })
    @PutMapping(value = "/users/{userId}", params = {"all", "noPwd"})
    public UserResource updateUser(@PathVariable(name = "userId") Long userId,
                                   @Valid @RequestBody SaveUserResource resource,
                                   @RequestParam(value = "all") String all,
                                   @RequestParam(value = "noPwd", required = false) String noPwd){
        User user = convertToEntity(resource);
        return convertToResource(userService.updateUser(userId, user));
    }

    @Operation(summary = "Update User", description = "Update user by his Id from Speedplanner", tags = { "users" })
    @PutMapping(value = "/users/{userId}", params = {"noPwd"})
    public UserResource updateUser(@PathVariable(name = "userId") Long userId,
                                   @Valid @RequestBody SaveUserFieldsResource fieldsResource,
                                   @RequestParam(value = "noPwd") String noPwd){
        User user = convertToEntity(fieldsResource);
        return convertToResource(userService.updateUserFields(userId, user));
    }

    /*@Operation(summary = "Update User's username and email.", description = "Updates only a user's Username and Email " +
            "fields by his Id from Speedplanner. This is needed for better functioning of the Profile screen" +
            "in our frontend", tags = { "users" })
    @PutMapping("/users/{userId}")
    public UserResource updateUserFields(@PathVariable(name = "userId") Long userId,
                                         @Valid @RequestBody SaveUserFieldsResource resource){
        User user = convertToEntity(resource);
        return convertToResource(userService.updateUser(userId, user));
    }*/

    @Operation(summary = "Delete User", description = "Delete user by his Id from Speedplanner", tags = { "users" })
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "userId") Long userId){
        return userService.deleteUser(userId);
    }

    @Operation(summary = "Get User by Username", description = "Get User by Username from Speedplanner", tags = { "users" })
    @GetMapping("/usersUsername/{username}")
    public UserResource getUserByUsername(@PathVariable(name = "username") String username) {
        return convertToResource(userService.getUserByUsername(username));
    }

    private User convertToEntity(SaveUserResource resource){
        return mapper.map(resource, User.class);
    }

    private User convertToEntity(SaveUserFieldsResource resource){
        return mapper.map(resource, User.class);
    }

    private UserResource convertToResource(User entity){
        return mapper.map(entity, UserResource.class);
    }
}

