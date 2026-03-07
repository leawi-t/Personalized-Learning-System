package com.Project.Personalized_Learning_System.user;

import com.Project.Personalized_Learning_System.subject.SubjectMapper;
import com.Project.Personalized_Learning_System.user.userDto.UserDetailsDto;
import com.Project.Personalized_Learning_System.user.userDto.UserRequestDto;
import com.Project.Personalized_Learning_System.user.userDto.UserResponseDto;
import com.Project.Personalized_Learning_System.user.userDto.UserUpdateDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = SubjectMapper.class)
public interface UserMapper {

    UserResponseDto toResponse(User user);

    UserDetailsDto toDetails(User user);

    User toEntity(UserRequestDto userRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(UserUpdateDto userUpdateDto, @MappingTarget User user);
}
