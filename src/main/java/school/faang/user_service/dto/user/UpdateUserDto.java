package school.faang.user_service.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateUserDto {
    private final String username;
    private final String email;
    private final String phone;
    private final String aboutMe;
    private final Long countryId;
    private final String city;
}
