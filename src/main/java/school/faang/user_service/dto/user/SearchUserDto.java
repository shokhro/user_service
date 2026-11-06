package school.faang.user_service.dto.user;

public record SearchUserDto(
        String aboutMePattern,
        String city,
        Integer experience) {
}
