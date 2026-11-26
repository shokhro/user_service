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
@RequestMapping("/api/v1/users")
public class UserSubscriptionController {
    private final UserSubscriptionService userSubscriptionService;
    private final UserContext userContext;

    @PostMapping("/{userId}/follow")
    public ResponseEntity<Void> followUser(@PathVariable long userId) {
        userSubscriptionService.followUser(userContext.getUserId(), userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/follow")
    public ResponseEntity<Void> unfollowUser(@PathVariable long userId) {
        userSubscriptionService.unfollowUser(userContext.getUserId(), userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<CountResponse> getFollowersCount(@PathVariable long userId) {
        CountResponse followersCount = userSubscriptionService.getFollowersCount(userId);
        return ResponseEntity.status(HttpStatus.OK).body(followersCount);
    }

    @GetMapping("/{userId}/followees/count")
    public ResponseEntity<CountResponse> getFolloweesCount(@PathVariable long userId) {
        CountResponse followeesCount = userSubscriptionService.getFolloweesCount(userId);
        return ResponseEntity.status(HttpStatus.OK).body(followeesCount);
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<UserDto>> getFollowers(@PathVariable long userId) {
        List<UserDto> followers = userSubscriptionService.getFollowers(userId);
        return ResponseEntity.status(HttpStatus.OK).body(followers);
    }

    @GetMapping("/{userId}/followees")
    public ResponseEntity<List<UserDto>> getFollowees(@PathVariable long userId) {
        List<UserDto> followees = userSubscriptionService.getFollowees(userId);
        return ResponseEntity.status(HttpStatus.OK).body(followees);
    }
}


























