package school.faang.user_service.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.CountResponse;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.service.user.UserSubscriptionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subscriptions")
public class UserSubscriptionController {
    private final UserSubscriptionService userSubscriptionService;
    private final UserContext userContext;

    @PostMapping("/{followeeId}")
    public ResponseEntity<Void> followUser(long followeeId) {
        userSubscriptionService.followUser(userContext.getUserId(), followeeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{followeeId}")
    public ResponseEntity<Void> unFollowUser(@PathVariable long followeeId) {
        userSubscriptionService.followUser(userContext.getUserId(), followeeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/followees/{followeeId}/amount")
    public CountResponse getFollowersCount(@PathVariable long followeeId) {
        return userSubscriptionService.getFollowersCount(followeeId);
    }

    @GetMapping("/followers/{followerId}/amount")
    public CountResponse getFolloweesCount(@PathVariable long followerId) {
        return userSubscriptionService.getFolloweesCount(followerId);
    }

    @GetMapping("/followees/{followeeId}/followers")
    public ResponseEntity<List<UserDto>> getFollowers(@PathVariable long followeeId) {
        List<UserDto> followers = userSubscriptionService.getFollowers(followeeId);
        return ResponseEntity.status(HttpStatus.OK).body(followers);
    }

    @GetMapping("/followers/{followerId}/followees")
    public ResponseEntity<List<UserDto>> getFollowee(@PathVariable long followerId) {
        List<UserDto> followees = userSubscriptionService.getFollowees(followerId);
        return ResponseEntity.status(HttpStatus.OK).body(followees);
    }
}


























