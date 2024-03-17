package am.smartcode.first_spring.service.user;


import am.smartcode.first_spring.exception.UserAlreadyExistsException;
import am.smartcode.first_spring.exception.ValidationException;
import am.smartcode.first_spring.exception.VerificationException;
import am.smartcode.first_spring.mapper.UserMapper;
import am.smartcode.first_spring.model.dto.user.ChangePasswordDto;
import am.smartcode.first_spring.model.dto.user.CreateUserDto;
import am.smartcode.first_spring.model.entity.UserEntity;
import am.smartcode.first_spring.repository.UserRepository;
import am.smartcode.first_spring.service.email.EmailService;
import am.smartcode.first_spring.util.RandomGenerator;
import am.smartcode.first_spring.util.encoder.MD5Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void login(String email, String password) {
        UserEntity user = userRepository.findByEmail(email);
        if (user != null) {
            if (!user.isVerified())
                throw new VerificationException("You must verify your account");
            if (!user.getPassword().equals(MD5Encoder.encode(password)))
                throw new ValidationException("Email or Password not valid");
        } else
            throw new ValidationException("Email or Password not valid");
    }

    @Override
    @Transactional
    public void register(CreateUserDto createUserDto) {

        UserEntity byEmail = userRepository.findByEmail(createUserDto.getEmail());
        if (byEmail != null)
            throw new UserAlreadyExistsException("User with this email already exists");

        UserEntity user = userMapper.toEntity(createUserDto);

        String code = RandomGenerator.generateNumericString(6);
        user.setVerified(false);
        user.setCode(code);
        user.setPassword(MD5Encoder.encode(user.getPassword()));

        emailService.sendEmail(user.getEmail(), "Verification", "Your code is " + code);

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void verify(String email, String code) {
        UserEntity byEmail = userRepository.findByEmail(email);
        if (!code.equals(byEmail.getCode())) {
            throw new VerificationException("Code is incorrect");
        }

        byEmail.setVerified(true);
        userRepository.save(byEmail);
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void changePassword(ChangePasswordDto changePasswordDto) {

    }
}

