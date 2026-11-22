package school.faang.user_service.filter.mentorshipRequest;

import org.springframework.stereotype.Component;
import school.faang.user_service.dto.mentorship.MentorshipRequestFilterDto;
import school.faang.user_service.entity.MentorshipRequest;

import java.util.Objects;
import java.util.stream.Stream;

@Component
public class MentorshipRequestRequesterFilter implements MentorshipRequestFilter{
    @Override
    public boolean isApplicable(MentorshipRequestFilterDto filterDto) {
        return filterDto.requesterId() != null;
    }

    @Override
    public Stream<MentorshipRequest> apply(Stream<MentorshipRequest> requests, MentorshipRequestFilterDto filterDto) {
        return requests.filter(mentorshipRequest ->
                Objects.equals(mentorshipRequest.getRequester().getId(), filterDto.requesterId()));
    }
}
