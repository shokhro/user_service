package school.faang.user_service.validator.mentorship;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import school.faang.user_service.entity.MentorshipRequest;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.repository.mentorship.MentorshipRequestRepository;
import school.faang.user_service.validator.user.UserValidator;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MentorshipRequestValidator {

    @Value("${mentorship-request.interval.months}")
    private int intervalMonths;
    private final MentorshipRequestRepository mentorshipRequestRepository;
    private final UserValidator userValidator;

    public void validateCreate(long requesterId, long receiverId) {
        validateNotSelfRequest(requesterId, receiverId);
        userValidator.validateUserExists(requesterId);
        Optional<MentorshipRequest> latestRequest =
                mentorshipRequestRepository.findLatestRequest(requesterId, receiverId);
        latestRequest.ifPresent(this::validateTimeInterval);
    }

    public void validateNotSelfRequest(long requesterId, long receiverId) {
        if (receiverId == requesterId) {
            throw new DataValidationException("You cannot send a mentorship request to yourself.");
        }
    }

    public void validateTimeInterval(MentorshipRequest latestRequest) {
        LocalDateTime nextAllowedTime = latestRequest.getCreatedAt().plusMonths(intervalMonths);
        if (nextAllowedTime.isBefore(LocalDateTime.now())) {
            throw new DataValidationException(
                    "You can send the next request after " + nextAllowedTime);
        }
    }
}
