package am.smartcode.first_spring.service.user;

import am.smartcode.first_spring.model.dto.user.CreateUserDto;
import am.smartcode.first_spring.model.dto.user.UpdateUserDto;
import am.smartcode.first_spring.model.dto.user.UserDto;
import am.smartcode.first_spring.model.entity.UserEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    UserDto create(CreateUserDto user);

    UserDto update(UpdateUserDto updateUserDto, int id);

    UserDto getById(int id);

    List<UserDto> getAll();

    void delete(int id);

    UserDto getByEmail(String email);

}
