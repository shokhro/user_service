package school.faang.user_service.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.faang.user_service.dto.CountResponse;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.mapper.UserMapper;
import school.faang.user_service.repository.SubscriptionRepository;
import school.faang.user_service.validator.subscription.UserSubscriptionValidator;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSubscriptionServiceImpl implements UserSubscriptionService{
    private final SubscriptionRepository subscriptionRepository;
    private final UserSubscriptionValidator userSubscriptionValidator;
    private final UserMapper userMapper;

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

    @Override
    public CountResponse getFollowersCount(long followeeId) {
        int amount = subscriptionRepository.findFollowersAmountByFolloweeId(followeeId);
        return new CountResponse(amount);
    }

    @Override
    public CountResponse getFolloweesCount(long followerId) {
        int amount = subscriptionRepository.findFolloweesAmountByFollowerId(followerId);
        return new CountResponse(amount);
    }

    @Override
    public List<UserDto> getFollowers(long followeeId) {
        return subscriptionRepository.findByFollowerId(followeeId)
                .map(userMapper::toUserDto)
                .toList();
    }


}













