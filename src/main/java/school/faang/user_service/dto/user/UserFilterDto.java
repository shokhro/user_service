package school.faang.user_service.dto.user;


public record UserFilterDto(
        String patternName,
        String phonePattern,
        int experienceMax,
        int experienceMin
) {
}
