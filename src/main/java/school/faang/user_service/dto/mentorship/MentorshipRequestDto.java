package school.faang.user_service.dto.mentorship;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.RequestStatus;

@Getter
@RequiredArgsConstructor
public class MentorshipRequestDto {
    private final Long id;
    private final String description;
    private final UserDto requester;
    private final UserDto receiver;
    private final RequestStatus status;
    private final String createdAt;
    private final String updatedAt;
}
