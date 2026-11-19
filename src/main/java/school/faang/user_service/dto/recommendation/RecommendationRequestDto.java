package school.faang.user_service.dto.recommendation;

import jakarta.validation.constraints.NotNull;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.RequestStatus;


public record RecommendationRequestDto(
        @NotNull Long id,
        String message,
        @NotNull UserDto requester,
        @NotNull UserDto receiver,
        @NotNull RequestStatus status,
        String createdAt,
        String updatedAt
) {
}
