package am.smartcode.first_spring.mapper;

import am.smartcode.first_spring.model.dto.user.CreateUserDto;
import am.smartcode.first_spring.model.dto.user.UpdateUserDto;
import am.smartcode.first_spring.model.dto.user.UserDto;
import am.smartcode.first_spring.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "birthday", source = "age")
    UserEntity toEntity(CreateUserDto createUserDto);

    @Mapping(target = "birthday", source = "age")
    void update(@MappingTarget UserEntity user, UpdateUserDto updateUserDto);

    UserDto toDto(UserEntity userEntity);
}
