package school.faang.user_service.service.user;

public interface UserSubscriptionService {

    void followUser(long followerId, long followeeId);

    void unFollowUser(long followerId, long followeeId);
}
