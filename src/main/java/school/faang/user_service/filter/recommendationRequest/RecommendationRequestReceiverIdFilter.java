package school.faang.user_service.filter.recommendationRequest;

import org.springframework.stereotype.Component;
import school.faang.user_service.dto.recommendation.RecommendationRequestFilterDto;
import school.faang.user_service.entity.recommendation.RecommendationRequest;

import java.util.Objects;
import java.util.stream.Stream;

@Component
public class RecommendationRequestReceiverIdFilter implements RecommendationRequestFilter{
    @Override
    public boolean isApplicable(RecommendationRequestFilterDto filterDto) {
        return filterDto.getReceiverId() != null;
    }

    @Override
    public Stream<RecommendationRequest> apply(
            Stream<RecommendationRequest> recommendationRequestStream,
            RecommendationRequestFilterDto filterDto
    ) {
        return recommendationRequestStream.filter(recommendationRequest ->
                Objects.equals(recommendationRequest.getReceiver().getId(), filterDto.getReceiverId()));
    }
}
