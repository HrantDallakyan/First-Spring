package am.smartcode.first_spring.service.user;

import am.smartcode.first_spring.exception.EntityNotFoundException;
import am.smartcode.first_spring.mapper.UserMapper;
import am.smartcode.first_spring.model.dto.user.CreateUserDto;
import am.smartcode.first_spring.model.dto.user.UpdateUserDto;
import am.smartcode.first_spring.model.dto.user.UserDto;
import am.smartcode.first_spring.model.entity.UserEntity;
import am.smartcode.first_spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto create(CreateUserDto user) {
        UserEntity entity = userMapper.toEntity(user);
        userRepository.save(entity);
        return userMapper.toDto(entity);
    }

    @Override
    @Transactional
    public UserDto update(UpdateUserDto updateUserDto, int id) {
        UserEntity userById = userRepository.findById(id).orElseThrow();

        userMapper.update(userRepository.save(userById), updateUserDto);
        userRepository.save(userById);
        return userMapper.toDto(userById);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getById(int id) {
        return userMapper.toDto(userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("")));
    }

    @Override
    @Transactional
    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getByEmail(String email) {
        return userMapper.toDto(userRepository.findByEmail(email));
    }


}
