package school.faang.user_service.service.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.user.CreateUserDto;
import school.faang.user_service.dto.user.UpdateUserDto;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.Country;
import school.faang.user_service.entity.User;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.exception.ForbiddenException;
import school.faang.user_service.mapper.UserMapper;
import school.faang.user_service.repository.CountryRepository;
import school.faang.user_service.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${user.password.min.length}")
    private int minPasswordLength;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final UserMapper userMapper;
    private final UserContext userContext;

    @Override
    public UserDto create(CreateUserDto userDto) {
        if (userDto.getPassword().length() < minPasswordLength) {
            throw new DataValidationException("Password should be more than " + minPasswordLength +
                    " symbols!");
        }
        User user = userMapper.toUser(userDto);
        Country country = countryRepository
                .findById(userDto
                        .getCountryId())
                .orElseThrow(() ->
                        new EntityNotFoundException(String
                                .format("Country %d not found", userDto.getCountryId())));

        user.setCountry(country);
        User userToSaved = userRepository.save(user);
        return userMapper.toUserDto(userToSaved);
    }

    @Override
    public UserDto update(Long id, UpdateUserDto userDto) {
        long requesterId = userContext.getUserId();
        if (requesterId != id) {
            throw new ForbiddenException("User " + requesterId + " doesn't match profile owner!");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String
                                .format("Country %d not found", id)));
        userMapper.update(userDto, user);
        Country country = countryRepository
                .findById(userDto
                        .getCountryId())
                .orElseThrow(() ->
                        new EntityNotFoundException(String
                                .format("Country %d not found", userDto.getCountryId())));
        user.setCountry(country);
        user = userRepository.save(user);
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String
                                .format("Country %d not found", id)));
        return userMapper.toUserDto(user);
    }
}


























