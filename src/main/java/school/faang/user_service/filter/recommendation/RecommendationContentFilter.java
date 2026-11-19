package school.faang.user_service.filter.recommendation;

import org.springframework.stereotype.Component;
import school.faang.user_service.dto.recommendation.RecommendationFilterDto;
import school.faang.user_service.entity.recommendation.Recommendation;

import java.util.stream.Stream;

@Component
public class RecommendationContentFilter implements RecommendationFilter{
    @Override
    public boolean isApplicable(RecommendationFilterDto recommendationFilterDto) {
        return recommendationFilterDto.contentContains().isBlank();
    }

    @Override
    public Stream<Recommendation> apply(
            Stream<Recommendation> recommendations,
            RecommendationFilterDto recommendationFilterDto
    ) {
        String pattern = recommendationFilterDto.contentContains().toLowerCase();

        return recommendations.filter(recommendation ->
                recommendation.getContent().toLowerCase().contains(pattern));
    }
}
