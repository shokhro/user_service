package school.faang.user_service.dto.mentorship;

import jakarta.validation.constraints.Positive;
import school.faang.user_service.entity.RequestStatus;

public record MentorshipRequestFilterDto(
        @Positive Long requesterId,
        @Positive Long receiverId,
        RequestStatus status
) {
}
