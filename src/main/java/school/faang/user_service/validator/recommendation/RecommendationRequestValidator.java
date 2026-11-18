package school.faang.user_service.validator.recommendation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.entity.RequestStatus;
import school.faang.user_service.entity.recommendation.RecommendationRequest;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.exception.ForbiddenException;
import school.faang.user_service.repository.recommendation.RecommendationRequestRepository;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RecommendationRequestValidator {

    private final RecommendationRequestRepository recommendationRequestRepository;
    private final UserContext userContext;

    @Value("${recommendation-request.interval.months}")
    private int intervalMonths;

    private final RecommendationRequestRepository repository;

    public void validateNotSelf(long requesterId, long receiverId) {
        if (requesterId == receiverId) {
            throw new DataValidationException("User cannot request a recommendation from himself");
        }
    }

    public void validateInterval(long requesterId, long receiverId) {
        repository.findFirstByRequesterIdAndReceiverIdOrderByCreatedAtDesc(requesterId, receiverId)
                .ifPresent(last -> {
                    LocalDateTime nextAllowedAt = last.getCreatedAt().plusMonths(intervalMonths);
                    if (nextAllowedAt.isAfter(LocalDateTime.now())) {
                        throw new DataValidationException(
                                "You can request a recommendation from the same user once per "
                                        + intervalMonths + " months. Next allowed date: " + nextAllowedAt);
                    }
                });
    }

    public RecommendationRequest findByIdOrElseThrows(long id) {
        return recommendationRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Recommendation request not found, id=" + id));
    }

    public void validateCurrentUserIsReceiver(RecommendationRequest recommendationRequest) {
        long currentUserId = userContext.getUserId();
        if (!Objects.equals(recommendationRequest.getReceiver().getId(), currentUserId)) {
            throw new ForbiddenException("Only the receiver can accept or reject this request");
        }
    }

    public void validateStatusIsPending(RecommendationRequest recommendationRequest) {
        if (recommendationRequest.getStatus() != RequestStatus.PENDING) {
            throw new DataValidationException("Only PENDING requests can be processed, current status: " + recommendationRequest.getStatus());
        }
    }


}
