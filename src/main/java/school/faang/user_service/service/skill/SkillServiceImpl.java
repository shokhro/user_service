package school.faang.user_service.service.skill;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.skill.CreateSkillDto;
import school.faang.user_service.dto.skill.SkillCandidateDto;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.entity.Skill;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.exception.ForbiddenException;
import school.faang.user_service.mapper.SkillMapper;
import school.faang.user_service.repository.SkillRepository;
import school.faang.user_service.repository.UserSkillGuaranteeRepository;
import school.faang.user_service.repository.recommendation.SkillOfferRepository;
import school.faang.user_service.validator.skill.SkillValidator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService{

    @Value("${min.skill.offers}")
    private int minSkillOffers;
    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;
    private final SkillOfferRepository skillOfferRepository;
    private final UserContext userContext;
    private final UserSkillGuaranteeRepository guaranteeRepository;
    private final SkillValidator skillValidator;

    @Override
    public SkillDto create(CreateSkillDto skillDto) {
        skillValidator.validateSkillByTitle(skillDto);
        Skill skill = skillMapper.toSkill(skillDto);
        Skill savedSkill = skillRepository.save(skill);
        log.info("called create method: title={}", skill.getTitle());
        return skillMapper.toSkillDto(savedSkill);
    }

    @Override
    public List<SkillCandidateDto> getOfferedSkills(long userId) {
        skillValidator.validateUserOwnership(userId);

        List<Skill> offeredSkills = skillRepository.findSkillsOfferedToUser(userId);

        Set<Long> seenIds = new HashSet<>();
        List<SkillCandidateDto> result = new ArrayList<>();
        for (Skill skill : offeredSkills) {
            if (seenIds.add(skill.getId())) {
                int offersAmount = skillOfferRepository.countAllOffersOfSkill(skill.getId(), userId);
                result.add(new SkillCandidateDto(skillMapper.toSkillDto(skill), offersAmount));
            }
        }
        return result;
    }

    @Override
    public List<SkillDto> getByUserId(Long userId) {
        List<Skill> allByUserId = skillRepository.findAllByUserId(userId);

        return allByUserId
                .stream()
                .map(skillMapper::toSkillDto)
                .toList();
    }

    @Override
    @Transactional
    public void acquireSkillFromOffers(long skillId, long userId) {
        log.info("acquireSkillFromOffers called: skillId={}, userId={}", skillId, userId);

        if (!skillRepository.existsById(skillId)) {
            throw new DataValidationException("Skill with id " + skillId + " not found");
        }

        if (skillRepository.findUserSkill(skillId, userId).isPresent()) {
            throw new DataValidationException("User already has this skill");
        }

        int offersAmount = skillOfferRepository.countAllOffersOfSkill(skillId, userId);
        if (minSkillOffers > offersAmount) {
            throw new DataValidationException("Skill was offered "
                    + offersAmount + " time(s), but at least "
                    + minSkillOffers + " required");
        }

        skillRepository.assignSkillToUser(skillId, userId);
        log.info("Skill {} assigned to user {}", skillId, userId);
    }
}























