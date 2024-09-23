package com.sahabuddin.blogappapis.services.impl;

import com.sahabuddin.blogappapis.entities.User;
import com.sahabuddin.blogappapis.exceptions.ResourceNotFoundException;
import com.sahabuddin.blogappapis.payloads.UserDto;
import com.sahabuddin.blogappapis.repositories.UserRepository;
import com.sahabuddin.blogappapis.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public UserDto getUserById(Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        return this.userConvertToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return this.userRepository.findAll().stream().map(this::userConvertToUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userDtoConvertToUser(userDto);
        return this.userConvertToUserDto(this.userRepository.save(user));
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        return this.userConvertToUserDto(this.userRepository.save(user));
    }

    @Override
    public void deleteUser(Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
        this.userRepository.delete(user);
    }

    private User userDtoConvertToUser(UserDto userDto) {
//        User user = new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
        return this.modelMapper.map(userDto, User.class);
    }

    private UserDto userConvertToUserDto(User user) {
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
//        return userDto;
        return this.modelMapper.map(user, UserDto.class);
    }
}
