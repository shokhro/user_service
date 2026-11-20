package school.faang.user_service.validator.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.user.CreateUserDto;
import school.faang.user_service.dto.user.UpdateUserDto;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.Country;
import school.faang.user_service.entity.User;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.exception.ForbiddenException;
import school.faang.user_service.repository.CountryRepository;
import school.faang.user_service.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final CountryRepository countryRepository;
    private final UserContext userContext;
    private final UserRepository userRepository;

    @Value("${user.password.min.length}")
    private int minPasswordLength;

    public void validatePasswordLength(CreateUserDto userDto) {
        if (userDto.password().length() < minPasswordLength) {
            throw new DataValidationException("Password should be more than " + minPasswordLength +
                    " symbols!");
        }
    }

    public Country findByIdCountry(long id) {
        return countryRepository
                .findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String
                                .format("Country %d not found", id)));
    }

    public void validateProfileOwner(long id) {
        long currentUserId = userContext.getUserId();
        if (currentUserId != id) {
            throw new ForbiddenException("User " + currentUserId + " doesn't match profile owner!");
        }
    }

    public User findByIdOrElseThrows(long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String
                                .format("Entity %d not found", id)));
    }
}
