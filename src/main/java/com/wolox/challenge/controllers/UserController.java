package com.wolox.challenge.controllers;

import com.wolox.challenge.models.dtos.UserDTO;
import com.wolox.challenge.services.external.UserExternalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@Api(tags = "Users of External Api")
public class UserController {

    private final UserExternalService userExternalService;

    @Autowired
    public UserController(UserExternalService userExternalService) {
        this.userExternalService = userExternalService;
    }

    @GetMapping
    @ApiOperation(value = "Get All User", response = UserDTO[].class)
    public List<UserDTO> getAllUsers() {
        return userExternalService.getAllUsers();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get User By Id", response = UserDTO.class)
    public UserDTO getUserById(@PathVariable(value = "id") Long userId) {
        return userExternalService.getUserById(userId);
    }
}
