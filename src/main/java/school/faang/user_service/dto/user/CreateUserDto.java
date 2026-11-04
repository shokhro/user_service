package school.faang.user_service.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CreateUserDto {
    private final String username;
    private final String email;
    private final String password;
    private final Long countryId;
    private final String phoneNumber;
}
