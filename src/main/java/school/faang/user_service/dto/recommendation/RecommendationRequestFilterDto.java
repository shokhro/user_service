package school.faang.user_service.dto.recommendation;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import school.faang.user_service.entity.RequestStatus;

@Getter
public class RecommendationRequestFilterDto {
    @Positive
    private Long requesterId;
    @Positive
    private Long receiverId;
    private String messageContains;
    private RequestStatus status;
}
