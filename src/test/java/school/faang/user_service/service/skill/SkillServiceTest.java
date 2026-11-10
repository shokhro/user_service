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
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.entity.Skill;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.mapper.SkillMapperImpl;
import school.faang.user_service.repository.SkillRepository;
import school.faang.user_service.repository.recommendation.SkillOfferRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SkillServiceTest {

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



//    @Test
//    public void testCreateWithExistingTitle() {
//        CreateSkillDto skillDto = new CreateSkillDto("Ruby");
//        when(skillRepository.existsByTitle(skillDto.title())).thenReturn(true);
//
//        assertThrows(DataValidationException.class, () -> skillService.create(skillDto));
//    }
//
//    @Test
//    public void testCreateSavesSkill() {
//        CreateSkillDto skillDto = new CreateSkillDto("Rust");
//        when(skillRepository.existsByTitle(skillDto.title())).thenReturn(false);
//        when(skillRepository.save(any(Skill.class)))
//                .thenAnswer(invocation -> invocation.getArgument(0));
//
//        SkillDto result = skillService.create(skillDto);
//
//        verify(skillRepository, times(1)).save(captor.capture());
//        Skill skill = captor.getValue();
//        assertEquals(skillDto.title(), skill.getTitle());
//        assertEquals(skillDto.title(), result.getTitle());
//    }


}

















