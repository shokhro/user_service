package school.faang.user_service.service.skill;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import school.faang.user_service.dto.skill.CreateSkillDto;
import school.faang.user_service.dto.skill.SkillCandidateDto;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.entity.Skill;
import school.faang.user_service.exception.ForbiddenException;
import school.faang.user_service.mapper.SkillMapperImpl;
import school.faang.user_service.repository.SkillRepository;
import school.faang.user_service.repository.recommendation.SkillOfferRepository;
import school.faang.user_service.validator.skill.SkillValidator;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SkillServiceTest {

    private static final long USER_ID = 13;

    @Mock
    private SkillRepository skillRepository;
    @Spy
    private SkillMapperImpl skillMapper;
    @Mock
    private SkillOfferRepository skillOfferRepository;
    @InjectMocks
    private SkillServiceImpl skillService;
    @Captor
    private ArgumentCaptor<Skill> captor;
    @Mock
    private SkillValidator skillValidator;

    @Test
    public void create_whenValidSkill_savesAndReturnsDto() {
        CreateSkillDto skillDto = new CreateSkillDto("Ruby");
        when(skillRepository.save(any(Skill.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        SkillDto result = skillService.create(skillDto);

        verify(skillRepository, times(1)).save(captor.capture());
        Skill skill = captor.getValue();
        assertEquals(skillDto.title(), skill.getTitle());
        assertEquals(skillDto.title(), result.getTitle());
    }

    @Test
    public void getOfferedSkills_whenValidIds_savesAndReturnsListSkillCandidateDto() {
        Skill firstSkill = new Skill();
        firstSkill.setId(10L);
        Skill secondSkill = new Skill();
        secondSkill.setId(20L);

        when(skillRepository.findSkillsOfferedToUser(USER_ID))
                .thenReturn(List.of(firstSkill, secondSkill));
        when(skillOfferRepository.countAllOffersOfSkill(firstSkill.getId(), USER_ID))
                .thenReturn(3);
        when(skillOfferRepository.countAllOffersOfSkill(secondSkill.getId(), USER_ID))
                .thenReturn(5);

        List<SkillCandidateDto> result = skillService.getOfferedSkills(USER_ID);

        assertEquals(2, result.size());
        assertEquals(3, result.get(0).getOffersAmount());
        assertEquals(10L, result.get(0).getSkill().getId());
        assertEquals(5, result.get(1).getOffersAmount());
        assertEquals(20L, result.get(1).getSkill().getId());
        verify(skillValidator, times(1)).validateUserOwnership(USER_ID);
        verify(skillRepository, times(1)).findSkillsOfferedToUser(USER_ID);
    }

    @Test
    public void getOfferedSkills_whenDuplicateSkills_skipsDuplicates() {
        Skill skill = new Skill();
        skill.setId(222L);

        when(skillRepository.findSkillsOfferedToUser(USER_ID))
                .thenReturn(List.of(skill, skill));
        when(skillOfferRepository.countAllOffersOfSkill(skill.getId(), USER_ID))
                .thenReturn(4);

        List<SkillCandidateDto> result = skillService.getOfferedSkills(USER_ID);

        assertEquals(1, result.size());
        assertEquals(222L, result.get(0).getSkill().getId());
        assertEquals(4, result.get(0).getOffersAmount());
        verify(skillValidator, times(1)).validateUserOwnership(USER_ID);
        verify(skillRepository, times(1))
                .findSkillsOfferedToUser(USER_ID);
        verify(skillOfferRepository, times(1))
                .countAllOffersOfSkill(222L, USER_ID);
    }

    @Test
    public void getOfferedSkills_whenNoSkills_returnsEmptyList() {
        when(skillRepository.findSkillsOfferedToUser(USER_ID))
                .thenReturn(Collections.emptyList());

        List<SkillCandidateDto> result = skillService.getOfferedSkills(USER_ID);

        assertTrue(result.isEmpty());
        verify(skillValidator,times(1)).validateUserOwnership(USER_ID);
        verifyNoInteractions(skillOfferRepository, skillMapper);
    }

    @Test
    public void getOfferedSkills_whenValidationFails_throwsForbiddenException() {
        doThrow(new ForbiddenException("You can only see what belongs to you!"))
                .when(skillValidator).validateUserOwnership(USER_ID);

        assertThrows(ForbiddenException.class, () -> skillService.getOfferedSkills(USER_ID));

        verifyNoInteractions(skillRepository, skillOfferRepository, skillMapper);
    }

    @Test
    public void getByUserId_whenValidUserId_returnsListSkillDto() {
        Skill firstSkill = new Skill();
        firstSkill.setId(10L);
        Skill secondSkill = new Skill();
        secondSkill.setId(20L);
        when(skillRepository.findAllByUserId(USER_ID))
                .thenReturn(List.of(firstSkill, secondSkill));

        List<SkillDto> result = skillService.getByUserId(USER_ID);

        assertEquals(2, result.size());
        assertEquals(10L, result.get(0).getId());
        assertEquals(20L, result.get(1).getId());
        verify(skillMapper, times(2)).toSkillDto(any(Skill.class));
        verify(skillRepository, times(1)).findAllByUserId(USER_ID);
        verifyNoMoreInteractions(skillRepository, skillMapper);
    }

    @Test
    public void getByUserId_whenNoSkills_returnsEmptyList() {
        when(skillRepository.findAllByUserId(USER_ID))
                .thenReturn(Collections.emptyList());

        List<SkillDto> result = skillService.getByUserId(USER_ID);

        assertTrue(result.isEmpty());
        verify(skillRepository, times(1)).findAllByUserId(USER_ID);
        verifyNoInteractions(skillMapper);
    }

    @Test
    public void acquireSkillFromOffers() {

    }



}

















