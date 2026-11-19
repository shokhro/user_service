package school.faang.user_service.dto.recommendation;

import jakarta.validation.constraints.Positive;

public record RecommendationFilterDto(
        String contentContains,
        @Positive Long receiverId,
        @Positive Long authorId) {
}
