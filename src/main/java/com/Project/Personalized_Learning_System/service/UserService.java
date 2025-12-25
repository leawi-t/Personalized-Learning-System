package com.Project.Personalized_Learning_System.service;

import com.Project.Personalized_Learning_System.dto.userDto.*;
import com.Project.Personalized_Learning_System.exception.ResourceNotFoundException;
import com.Project.Personalized_Learning_System.mapper.UserMapper;
import com.Project.Personalized_Learning_System.model.User;
import com.Project.Personalized_Learning_System.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepo userRepo, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }

    public User getUserEntityById(long userId){
        return userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
    }

    public List<UserResponseDto> getAllUsers(){
        return userMapper.userToResponse(userRepo.findAll());
    }

    public UserDetailsDto getUserById(long userId){
        User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
        return userMapper.toDetails(user);
    }

    public UserDetailsDto getUserByUsername(String username){
        User user = userRepo.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User not found"));
        return userMapper.toDetails(user);
    }

    public UserDetailsDto getUserByEmail(String email){
        User user = userRepo.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found"));
        return userMapper.toDetails(user);
    }

    public UserDetailsDto createUser(CreateUserDto createUserDto){
        User user = userMapper.toEntity(createUserDto);
        return userMapper.toDetails(userRepo.save(user));
    }

    public UserDetailsDto updateUser(UserUpdateDto userUpdateDto, long userId){
        User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
        userMapper.updateUser(userUpdateDto, user);
        return userMapper.toDetails(userRepo.save(user));
    }

    public void deleteUser(long userId) {
        if(!userRepo.existsById(userId)){
            throw new ResourceNotFoundException("User not found");
        }
        userRepo.deleteById(userId);
    }
}
