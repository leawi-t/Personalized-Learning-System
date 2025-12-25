package com.Project.Personalized_Learning_System.mapper;

import com.Project.Personalized_Learning_System.dto.userDto.*;
import com.Project.Personalized_Learning_System.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface UserMapper {

    UserResponseDto toResponse(User user);

    List<UserResponseDto> userToResponse(List<User> users);

    UserDetailsDto toDetails(User user);

    User toEntity(CreateUserDto createUserDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(UserUpdateDto userUpdateDto, @MappingTarget User user);
}
