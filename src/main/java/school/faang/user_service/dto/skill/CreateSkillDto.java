package school.faang.user_service.dto.skill;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NonNull;

public record CreateSkillDto(
        @NonNull @NotBlank @Size(min = 1, max = 256) String title
) {
}
