package school.faang.user_service.service.mentorship;

import school.faang.user_service.dto.mentorship.CreateMentorshipRequestDto;
import school.faang.user_service.dto.mentorship.MentorshipRequestDto;

public interface MentorshipRequestService {

    MentorshipRequestDto create(CreateMentorshipRequestDto requestDto);
}
