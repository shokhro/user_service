package school.faang.user_service.validator.subscription;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.repository.SubscriptionRepository;
import school.faang.user_service.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserSubscriptionValidator {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public void validateFollow(long followerId, long followeeId) {
        if (followerId == followeeId) {
            throw new DataValidationException("You cannot subscribe to yourself.");
        }
        if (!userRepository.existsById(followeeId)) {
            throw new EntityNotFoundException("User not found, id: " + followeeId);
        }
        if (subscriptionRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId)) {
            throw new DataValidationException("You are already subscribed to user " + followeeId);
        }
    }

    public void validateUnfollow(long followerId, long followeeId) {
        if (followerId == followeeId) {
            throw new DataValidationException("A user cannot unsubscribe from themselves.");
        }
        if (!userRepository.existsById(followeeId)) {
            throw new EntityNotFoundException("User not found, id: " + followeeId);
        }
        if (!subscriptionRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId)) {
            throw new DataValidationException("You are not subscribed to user " + followeeId);
        }
    }
}





















