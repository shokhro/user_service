package school.faang.user_service.dto.skill;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import school.faang.user_service.dto.user.UserDto;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SkillDto {
    private final Long id;
    private final String title;
    private final List<UserDto> guarantors;
}
