package school.faang.user_service.validator.mentorship;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import school.faang.user_service.entity.MentorshipRequest;
import school.faang.user_service.entity.RequestStatus;
import school.faang.user_service.entity.User;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.exception.ForbiddenException;
import school.faang.user_service.repository.mentorship.MentorshipRequestRepository;
import school.faang.user_service.validator.user.UserValidator;

import java.time.LocalDateTime;
import java.util.Objects;
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

    private void validateNotSelfRequest(long requesterId, long receiverId) {
        if (receiverId == requesterId) {
            throw new DataValidationException("You cannot send a mentorship request to yourself.");
        }
    }

    private void validateTimeInterval(MentorshipRequest latestRequest) {
        LocalDateTime nextAllowedTime = latestRequest.getCreatedAt().plusMonths(intervalMonths);
        if (LocalDateTime.now().isBefore(nextAllowedTime)) {
            throw new DataValidationException(
                    "You can send the next request after " + nextAllowedTime);
        }
    }

    public void validateAccept(MentorshipRequest mentorshipRequest, long currentUserId) {
        if (!Objects.equals(mentorshipRequest.getReceiver().getId(), currentUserId)) {
            throw new ForbiddenException("Only the receiver can accept this request");
        }
        if (mentorshipRequest.getStatus() != RequestStatus.PENDING) {
            throw new DataValidationException("Request is already processed");
        }
        User mentor = mentorshipRequest.getReceiver();
        User mentee = mentorshipRequest.getRequester();
        if (mentor.getMentees().contains(mentee)) {
            throw new DataValidationException("Already mentor-mentee");
        }
    }

    public void validateReject(MentorshipRequest mentorshipRequest, long currentUser) {
        User mentor = mentorshipRequest.getReceiver();
        if (!Objects.equals(mentor.getId(), currentUser)) {
            throw new ForbiddenException("Only the receiver can reject this request");
        }
    }
}