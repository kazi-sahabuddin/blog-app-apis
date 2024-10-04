package com.sahabuddin.blogappapis.services.impl;

import com.sahabuddin.blogappapis.entities.User;
import com.sahabuddin.blogappapis.exceptions.ApiException;
import com.sahabuddin.blogappapis.exceptions.ResourceNotFoundException;
import com.sahabuddin.blogappapis.payloads.SignInRequest;
import com.sahabuddin.blogappapis.payloads.UserDto;
import com.sahabuddin.blogappapis.repositories.UserRepository;
import com.sahabuddin.blogappapis.security.JwtTokenHelper;
import com.sahabuddin.blogappapis.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtTokenHelper jwtTokenHelper;

    private final PasswordEncoder passwordEncoder;

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userConvertToUserDto(this.userRepository.save(user));
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAbout(userDto.getAbout());
        return this.userConvertToUserDto(this.userRepository.save(user));
    }

    @Override
    public void deleteUser(Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
        this.userRepository.delete(user);
    }

    @Override
    public String verify(SignInRequest request) {
        log.info("Verifying user: {}", request);
        log.info(userDetailsService.loadUserByUsername(request.getUsername()).getUsername());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        try{
            log.info("Attempting to verify user {}", usernamePasswordAuthenticationToken);
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            log.info("Authentication Successful");
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            return jwtTokenHelper.generateToken(userDetails);
        } catch (BadCredentialsException e) {
            log.error(e.getMessage());
           throw new ApiException("Invalid username or password");
        }
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
