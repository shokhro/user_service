package school.faang.user_service.dto.mentorship;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MentorshipDto(@Positive @NotNull long mentorId, @Positive @NotNull long menteeId) {
}
