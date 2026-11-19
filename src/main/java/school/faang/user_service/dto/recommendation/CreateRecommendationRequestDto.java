package school.faang.user_service.dto.recommendation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.NonNull;

import java.util.List;

public record CreateRecommendationRequestDto(
        @NotBlank @Size(max = 4096) String message,
        @NonNull @Positive Long receiverId,
        @Size(max = 20) List<@NotNull @Positive Long> skillIds
) {
}
