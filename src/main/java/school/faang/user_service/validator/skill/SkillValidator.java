package school.faang.user_service.validator.skill;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.skill.CreateSkillDto;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.exception.ForbiddenException;
import school.faang.user_service.repository.SkillRepository;
import school.faang.user_service.repository.recommendation.SkillOfferRepository;

@Component
@RequiredArgsConstructor
public class SkillValidator {

    @Value("${min.skill.offers}")
    private int minSkillOffers;
    private final SkillRepository skillRepository;
    private final UserContext userContext;
    private final SkillOfferRepository skillOfferRepository;

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

    public void validateSkillAcquisitionFromOffers(long skillId, long userId) {
        validateSkillExists(skillId);
        validateUserDoesNotHaveSkill(skillId, userId);
        validateEnoughOffers(skillId, userId);
    }

    private void validateSkillExists(long skillId) {
        if (!skillRepository.existsById(skillId)) {
            throw new DataValidationException("Skill with id " + skillId + " not found");
        }
    }

    private void validateUserDoesNotHaveSkill(long skillId, long userId) {
        if (skillRepository.findUserSkill(skillId, userId).isPresent()) {
            throw new DataValidationException("User already has this skill");
        }
    }

    private void validateEnoughOffers(long skillId, long userId) {
        int offersAmount = skillOfferRepository.countAllOffersOfSkill(skillId, userId);
        if (offersAmount < minSkillOffers) {
            throw new DataValidationException("Skill was offered "
                    + offersAmount + " time(s), but at least "
                    + minSkillOffers + " required");
        }
    }

}
