package school.faang.user_service.validator.user;

import org.springframework.stereotype.Component;
import school.faang.user_service.dto.user.CreateUserDto;
import school.faang.user_service.dto.user.UpdateUserDto;
import school.faang.user_service.exception.DataValidationException;

@Component
public class UserValidator {

    public void validateCreate(CreateUserDto dto) {
        validateCommonFields(dto.username(), dto.email(), dto.countryId());
        requireNotBlank(dto.password(), "Password should be present!");
    }

    public void validateUpdate(UpdateUserDto dto) {
        validateCommonFields(dto.username(), dto.email(), dto.countryId());
    }

    private void validateCommonFields(String username, String email, Long countryId) {
        requireNotBlank(username, "Username should be present!");
        requireNotBlank(email, "Email should be present!");
        if (countryId == null) {
            throw new DataValidationException("Country should be present!");
        }
    }

    private void requireNotBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new DataValidationException(message);
        }
    }
}
