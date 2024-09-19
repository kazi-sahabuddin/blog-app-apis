package com.sahabuddin.blogappapis.controllers;

import com.sahabuddin.blogappapis.payloads.UserDto;
import com.sahabuddin.blogappapis.services.UserService;
import com.sahabuddin.blogappapis.services.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto userDtoSaved = userService.createUser(userDto);
        return new ResponseEntity<>(userDtoSaved, HttpStatus.CREATED);
    }
}
