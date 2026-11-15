package school.faang.user_service.dto.recommendation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RecommendationDto(@Positive @NotNull Long id, @Positive @NotNull Long authorId,
                                @Positive @NotNull Long receiverId, @NotBlank String content) {
}
