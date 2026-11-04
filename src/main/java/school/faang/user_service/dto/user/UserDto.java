package school.faang.user_service.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserDto {
    private final Long id;
    private final String username;
    private final String phoneNumber;
    private final String email;
    private final String aboutMe;
}
