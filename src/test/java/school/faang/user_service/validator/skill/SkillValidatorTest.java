package school.faang.user_service.validator.skill;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.skill.CreateSkillDto;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.exception.ForbiddenException;
import school.faang.user_service.repository.SkillRepository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SkillValidatorTest {

    private static final long SKILL_ID = 1L;
    private static final long USER_ID = 10L;

    @Mock
    private UserContext userContext;
    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillValidator skillValidator;

    @Test
    public void validateSkillByTitle_whenExistingTitle_throwsException() {
        CreateSkillDto skillDto = new CreateSkillDto("Rust");
        when(skillRepository.existsByTitle(skillDto.title()))
                .thenReturn(true);

        DataValidationException exception = assertThrows(DataValidationException.class,
                () -> skillValidator.validateSkillByTitle(skillDto));

        assertEquals("Such a skill already exists!", exception.getMessage());
        verify(skillRepository, times(1)).existsByTitle(skillDto.title());
    }

    @Test
    public void validateSkillByTitle_whenNotExistingTitle_doesNotThrow() {
        CreateSkillDto skillDto = new CreateSkillDto("Go");
        when(skillRepository.existsByTitle(skillDto.title()))
                .thenReturn(false);

        skillValidator.validateSkillByTitle(skillDto);

        verify(skillRepository, times(1)).existsByTitle(skillDto.title());
    }

    @Test
    public void validateUserOwnership_whenIdsNotEqual_throwException() {
        long userId = 8;
        when(userContext.getUserId()).thenReturn(13L);

        ForbiddenException exception = assertThrows(ForbiddenException.class,
                () -> skillValidator.validateUserOwnership(userId));

        assertEquals("You can only see what belongs to you!", exception.getMessage());
    }

    @Test
    public void validateUserOwnership_whenIdsEqual_doesNotThrow() {
        long userId = 13L;
        when(userContext.getUserId()).thenReturn(13L);

        assertDoesNotThrow(() -> skillValidator.validateUserOwnership(userId));
    }













}

























