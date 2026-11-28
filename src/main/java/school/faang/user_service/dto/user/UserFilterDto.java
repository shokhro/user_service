package school.faang.user_service.dto.user;


import jakarta.validation.constraints.Positive;

public record UserFilterDto(
        String patternName,
        String phonePattern,
        @Positive Integer experienceMax,
        @Positive Integer experienceMin
) {
}
