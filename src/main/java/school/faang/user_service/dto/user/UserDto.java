package school.faang.user_service.dto.user;

public record UserDto(Long id,
                      String username,
                      String phone,
                      String email,
                      String aboutMe) {
}
