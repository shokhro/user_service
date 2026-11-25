package school.faang.user_service.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import school.faang.user_service.dto.user.CreateUserDto;
import school.faang.user_service.dto.user.SearchUserDto;
import school.faang.user_service.dto.user.UpdateUserDto;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.Country;
import school.faang.user_service.entity.User;
import school.faang.user_service.filter.user.UserFilter;
import school.faang.user_service.mapper.UserMapper;
import school.faang.user_service.repository.UserRepository;
import school.faang.user_service.validator.user.UserValidator;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserValidator userValidator;
    private final List<UserFilter> userFilters;

    @Override
    public UserDto create(CreateUserDto userDto) {
        userValidator.validatePasswordLength(userDto);
        User user = userMapper.toUser(userDto);
        Country country = userValidator.findByIdCountry(userDto.countryId());
        user.setCountry(country);
        User savedUser = userRepository.save(user);
        log.info("User created: id={}", savedUser.getId());
        return userMapper.toUserDto(savedUser);
    }

    @Override
    public UserDto update(Long id, UpdateUserDto userDto) {
        userValidator.validateProfileOwner(id);
        User user = userValidator.validateUserExists(id);
        userMapper.update(userDto, user);
        Country country = userValidator.findByIdCountry(userDto.countryId());
        user.setCountry(country);
        user = userRepository.save(user);
        log.info("User updated: id={}", id);
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto getById(Long id) {
        User user = userValidator.validateUserExists(id);
        return userMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getUsers(SearchUserDto searchUserDto) {
        Stream<User> users = userRepository.findAll().stream();

        for (UserFilter filter : userFilters) {
            if (filter.isApplicable(searchUserDto)) {
                users = filter.apply(users, searchUserDto);
            }
        }

        return users
                .map(userMapper::toUserDto)
                .toList();
    }
}


























