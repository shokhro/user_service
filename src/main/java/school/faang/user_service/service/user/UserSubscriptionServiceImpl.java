package school.faang.user_service.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.faang.user_service.repository.SubscriptionRepository;
import school.faang.user_service.validator.subscription.UserSubscriptionValidator;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSubscriptionServiceImpl implements UserSubscriptionService{
    private final SubscriptionRepository subscriptionRepository;
    private final UserSubscriptionValidator userSubscriptionValidator;

    @Override
    @Transactional
    public void followUser(long followerId, long followeeId) {
        userSubscriptionValidator.validateFollow(followerId, followeeId);
        subscriptionRepository.followUser(followerId, followeeId);
        log.info("Follow user, followerId={}, followeeId={}", followerId, followeeId);
    }

    @Override
    @Transactional
    public void unFollowUser(long followerId, long followeeId) {
        userSubscriptionValidator.validateUnFollow(followerId, followeeId);
        subscriptionRepository.unfollowUser(followerId, followeeId);
        log.info("UnFollow user, followerId={}, followeeId={}", followerId, followeeId);
    }


}













