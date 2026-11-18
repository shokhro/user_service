package school.faang.user_service.dto.recommendation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateRecommendationRequestDto(
        @NotBlank String message,
        @Positive Long receiverId
) {
}
