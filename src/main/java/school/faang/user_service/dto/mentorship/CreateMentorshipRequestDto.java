package school.faang.user_service.dto.mentorship;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateMentorshipRequestDto(
        @NotBlank @Size(max = 4096) String description,
        @Positive @NotNull Long mentorId
) {
}
