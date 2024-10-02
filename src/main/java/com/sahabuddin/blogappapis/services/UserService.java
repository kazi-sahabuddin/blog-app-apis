package com.sahabuddin.blogappapis.services;

import com.sahabuddin.blogappapis.payloads.SignInRequest;
import com.sahabuddin.blogappapis.payloads.UserDto;

import java.util.List;

public interface UserService {


    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, Long userId);

    void deleteUser(Long userId);

    String verify(SignInRequest request);
}
