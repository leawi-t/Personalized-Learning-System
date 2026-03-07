package com.Project.Personalized_Learning_System.user;

import com.Project.Personalized_Learning_System.common.exception.customException.EmailAlreadyInUseException;
import com.Project.Personalized_Learning_System.common.exception.customException.ResourceNotFoundException;
import com.Project.Personalized_Learning_System.user.userDto.UserDetailsDto;
import com.Project.Personalized_Learning_System.user.userDto.UserRequestDto;
import com.Project.Personalized_Learning_System.user.userDto.UserResponseDto;
import com.Project.Personalized_Learning_System.user.userDto.UserUpdateDto;
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
        return userMapper.toDetails(userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User not found")));
    }

    public UserDetailsDto getUserByUsername(String username){
        return userMapper.toDetails(userRepo.findByUsername(username)
                .orElseThrow(()->new ResourceNotFoundException("User not found")));
    }

    public UserDetailsDto getUserByEmail(String email){;
        return userMapper.toDetails( userRepo.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("User not found")));
    }

    @Transactional
    public UserDetailsDto registerUser(UserRequestDto userRequestDto){
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
