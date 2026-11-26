package school.faang.user_service.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.faang.user_service.dto.CountResponse;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.User;
import school.faang.user_service.mapper.UserMapper;
import school.faang.user_service.repository.SubscriptionRepository;
import school.faang.user_service.validator.subscription.UserSubscriptionValidator;

import java.util.List;
import java.util.stream.Stream;

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
    public void unfollowUser(long followerId, long followeeId) {
        userSubscriptionValidator.validateUnfollow(followerId, followeeId);
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
    @Transactional(readOnly = true)
    public List<UserDto> getFollowers(long followeeId) {
        try (Stream<User> users = subscriptionRepository.findByFolloweeId(followeeId)) {
            return users.map(userMapper::toUserDto).toList();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getFollowees(long followerId) {
        try (Stream<User> users = subscriptionRepository.findByFollowerId(followerId)) {
            return users.map(userMapper::toUserDto).toList();
        }
    }


}













