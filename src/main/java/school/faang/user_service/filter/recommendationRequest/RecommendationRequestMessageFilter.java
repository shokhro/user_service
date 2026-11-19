package school.faang.user_service.filter.recommendationRequest;

import org.springframework.stereotype.Component;
import school.faang.user_service.dto.recommendation.RecommendationRequestFilterDto;
import school.faang.user_service.entity.recommendation.RecommendationRequest;

import java.util.List;
import java.util.stream.Stream;

@Component
public class RecommendationRequestMessageFilter implements RecommendationRequestFilter{
    @Override
    public boolean isApplicable(RecommendationRequestFilterDto filterDto) {
        return filterDto.getMessageContains() != null
                && !filterDto.getMessageContains().isBlank();
    }

    @Override
    public Stream<RecommendationRequest> apply(
            Stream<RecommendationRequest> requests,
            RecommendationRequestFilterDto filterDto
    ) {
        String text = filterDto.getMessageContains().toLowerCase();
        return requests.filter(r ->
                r.getMessage() != null && r.getMessage().toLowerCase().contains(text));
    }
}
