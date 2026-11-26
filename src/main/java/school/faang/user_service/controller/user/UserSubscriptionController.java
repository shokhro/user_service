package school.faang.user_service.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.service.user.UserSubscriptionService;

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
}
