package school.faang.user_service.service.skill;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.skill.CreateSkillDto;
import school.faang.user_service.dto.skill.SkillCandidateDto;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.entity.Skill;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.exception.ForbiddenException;
import school.faang.user_service.mapper.SkillMapper;
import school.faang.user_service.repository.SkillRepository;
import school.faang.user_service.repository.recommendation.SkillOfferRepository;

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

    @Override
    public SkillDto create(CreateSkillDto skillDto) {
        if (skillRepository.existsByTitle(skillDto.title())) {
            throw new DataValidationException("Such a skill already exists!");
        }
        Skill skill = skillMapper.toSkill(skillDto);
        Skill savedSkill = skillRepository.save(skill);
        log.info("called create method: title={}", skill.getTitle());
        return skillMapper.toSkillDto(savedSkill);
    }

    @Override
    public List<SkillCandidateDto> getOfferedSkills(long userId) {
        if (userContext.getUserId() != userId) {
            throw new ForbiddenException("You can only see what belongs to you!");
        }

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
    public void acquireSkillFromOffers(long skillId, long userId) {
        if (skillRepository.existsById(skillId)) {
            throw new DataValidationException("Such a skill already exists!");
        }

        log.info("called assignSkillToUser, skillId={}, userId={}", skillId, userId);
        skillRepository.assignSkillToUser(skillId, userId);
    }
}























