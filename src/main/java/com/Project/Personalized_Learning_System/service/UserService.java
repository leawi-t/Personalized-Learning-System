package com.Project.Personalized_Learning_System.service;

import com.Project.Personalized_Learning_System.dto.userDto.*;
import com.Project.Personalized_Learning_System.exception.EmailAlreadyInUseException;
import com.Project.Personalized_Learning_System.exception.ResourceNotFoundException;
import com.Project.Personalized_Learning_System.mapper.UserMapper;
import com.Project.Personalized_Learning_System.model.User;
import com.Project.Personalized_Learning_System.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public User getUserEntityById(long userId){
        return userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
    }

    public Page<UserResponseDto> getAllUsers(Pageable pageable){
        return userRepo.findAll(pageable).map(userMapper::toResponse);
    }

    public UserDetailsDto getUserById(long userId){
        User user = getUserEntityById(userId);
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

    @Transactional
    public UserDetailsDto createUser(UserRequestDto userRequestDto){
        if(userRepo.findByEmail(userRequestDto.email()).isPresent()) {
            throw new EmailAlreadyInUseException("Email is already taken");
        }
        User user = userMapper.toEntity(userRequestDto);
        return userMapper.toDetails(userRepo.save(user));
    }

    @Transactional
    public UserDetailsDto updateUser(UserUpdateDto userUpdateDto, long userId){
        User user = getUserEntityById(userId);
        userMapper.updateUser(userUpdateDto, user);
        return userMapper.toDetails(userRepo.save(user));
    }

    @Transactional
    public void deleteUser(long userId) {
        if(!userRepo.existsById(userId)){
            throw new ResourceNotFoundException("User not found");
        }
        userRepo.deleteById(userId);
    }
}
