package school.faang.user_service.filter.mentorshipRequest;

import school.faang.user_service.dto.mentorship.MentorshipRequestFilterDto;
import school.faang.user_service.entity.MentorshipRequest;

import java.util.stream.Stream;

public interface MentorshipRequestFilter {

    boolean isApplicable(MentorshipRequestFilterDto filterDto);

    Stream<MentorshipRequest> apply(
            Stream<MentorshipRequest> requests,
            MentorshipRequestFilterDto filterDto
    );
}
