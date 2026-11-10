package school.faang.user_service.validator.skill;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.skill.CreateSkillDto;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.exception.ForbiddenException;
import school.faang.user_service.repository.SkillRepository;

@Component
@RequiredArgsConstructor
public class SkillValidator {
    private final SkillRepository skillRepository;
    private final UserContext userContext;

    public void validateSkillByTitle(CreateSkillDto skillDto) {
        if (skillRepository.existsByTitle(skillDto.title())) {
            throw new DataValidationException("Such a skill already exists!");
        }
    }

    public void validateUserOwnership(long userId) {
        if (userContext.getUserId() != userId) {
            throw new ForbiddenException("You can only see what belongs to you!");
        }
    }

}
