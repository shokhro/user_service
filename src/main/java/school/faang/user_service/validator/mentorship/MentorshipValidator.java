package school.faang.user_service.validator.mentorship;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.exception.ForbiddenException;

@Component
@RequiredArgsConstructor
public class MentorshipValidator {
    private final UserContext userContext;

    public void validateMentorAndMenteeDiffer(long mentorId, long menteeId) {
        if (mentorId == menteeId) {
            throw new DataValidationException("User cannot mentor themselves");
        }
    }

    public void validateCanDeleteMentorship(long menteeId, long mentorId) {
        long currentUser = userContext.getUserId();
        if (currentUser != mentorId && currentUser != menteeId) {
            throw new ForbiddenException("Only mentor or mentee can delete the mentorship");
        }
    }

}
