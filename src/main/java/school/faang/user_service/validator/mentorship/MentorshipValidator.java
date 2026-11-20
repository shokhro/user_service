package school.faang.user_service.validator.mentorship;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.faang.user_service.exception.DataValidationException;

@Component
@RequiredArgsConstructor
public class MentorshipValidator {


    public void validateMentorAndMenteeDiffer(long mentorId, long menteeId) {
        if (mentorId == menteeId) {
            throw new DataValidationException("User cannot mentor themselves");
        }
    }

}
