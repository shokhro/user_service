package school.faang.user_service.validator.recommendation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.entity.recommendation.Recommendation;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.exception.ForbiddenException;
import school.faang.user_service.repository.recommendation.RecommendationRepository;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RecommendationValidator {

    @Value("${recommendation.interval.months}")
    private int recommendationIntervalMonths;
    private final RecommendationRepository recommendationRepository;
    private final UserContext userContext;

    public void validateNotSelf(long authorId, long receiverId) {
        if (receiverId == authorId) {
            throw new DataValidationException("User cannot recommend himself");
        }
    }

    public void validateRecommendationInterval(long authorId, long receiverId) {
        Optional<Recommendation> lastRecommendation = recommendationRepository.
                findFirstByAuthorIdAndReceiverIdOrderByCreatedAtDesc(authorId, receiverId);

        if (lastRecommendation.isEmpty()) return;

        LocalDateTime nextAllowedAt
                = lastRecommendation.get().getCreatedAt().plusMonths(recommendationIntervalMonths);
        if (nextAllowedAt.isAfter(LocalDateTime.now())) {
            throw new DataValidationException(
                    "You can recommend the same user only once per "
                            + recommendationIntervalMonths + " months. Next allowed date: " + nextAllowedAt
            );
        }
    }

    public void validateExisting(long recommendationId) {
        if (!recommendationRepository.existsById(recommendationId)) {
            throw new EntityNotFoundException("Entity not found");
        }
    }

    public void validateAuthor(Recommendation recommendation) {
        if (!Objects.equals(recommendation.getAuthor().getId(), userContext.getUserId())) {
            throw new ForbiddenException("Only the author can modify the recommendation");
        }
    }
}












