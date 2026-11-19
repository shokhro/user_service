package school.faang.user_service.dto.recommendation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RejectionDto(@NotBlank @Size(max = 4096) String reason) {
}
