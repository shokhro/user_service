package school.faang.user_service.dto.skill;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateSkillDto(@Size(min = 1, max = 256) @NotBlank String title) {

}
