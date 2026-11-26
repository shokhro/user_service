package school.faang.user_service.validator.subscription;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.faang.user_service.entity.User;
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
        User followee = userRepository.findById(followeeId)
                .orElseThrow(() -> new EntityNotFoundException("User not found, id: " + followeeId));
        if (followee.getFollowers().contains(followee)) {
            throw new DataValidationException("You are already subscribed");
        }
        boolean exists = subscriptionRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId);
        if (!exists) {
            throw new EntityNotFoundException("Entity not found");
        }
    }

    public void validateUnFollow(long followerId, long followeeId) {
        if (followerId == followeeId) {
            throw new DataValidationException("A user cannot unsubscribe from themselves.");
        }
        User followee = userRepository.findById(followeeId)
                .orElseThrow(() -> new EntityNotFoundException("User not found, id: " + followeeId));
        if (!followee.getFollowers().contains(followee)) {
            throw new DataValidationException("You are not subscribed.");
        }
    }
}





















