package school.faang.user_service.service.recommendation.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.recommendation.CreateRecommendationDto;
import school.faang.user_service.dto.recommendation.RecommendationDto;
import school.faang.user_service.dto.recommendation.RecommendationFilterDto;
import school.faang.user_service.dto.recommendation.UpdateRecommendationDto;
import school.faang.user_service.entity.recommendation.Recommendation;
import school.faang.user_service.filter.recommendation.RecommendationFilter;
import school.faang.user_service.mapper.RecommendationMapper;
import school.faang.user_service.repository.recommendation.RecommendationRepository;
import school.faang.user_service.service.recommendation.RecommendationService;
import school.faang.user_service.validator.recommendation.RecommendationValidator;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final RecommendationMapper recommendationMapper;
    private final UserContext userContext;
    private final RecommendationValidator recommendationValidator;
    private final List<RecommendationFilter> recommendationFilter;

    @Override
    @Transactional
    public RecommendationDto create(CreateRecommendationDto recommendationDto) {
        long receiverId = recommendationDto.receiverId();
        long authorId = userContext.getUserId();

        recommendationValidator.validateNotSelf(authorId, receiverId);
        recommendationValidator.validateRecommendationInterval(authorId, receiverId);

        Long recommendationId = recommendationRepository.create(
                authorId,
                receiverId,
                recommendationDto.content()
        );

        Recommendation savedRecommendation = findByIdOrElseThrow(recommendationId);
        log.info("Called create, content={}, receiverId={}", recommendationDto.content(), receiverId);
        return recommendationMapper.toRecommendationDto(savedRecommendation);
    }

    @Override
    public RecommendationDto update(
            long recommendationId,
            UpdateRecommendationDto recommendationDto)
    {
        Recommendation savedRecommendation = findByIdOrElseThrow(recommendationId);

        recommendationValidator.validateAuthor(savedRecommendation);

        savedRecommendation.setContent(recommendationDto.content());
        Recommendation saved = recommendationRepository.save(savedRecommendation);
        log.info("Called update, recommendationId={}", recommendationId);
        return recommendationMapper.toRecommendationDto(saved);
    }

    public void delete(long recommendationId) {
        Recommendation savedRecommendation = findByIdOrElseThrow(recommendationId);
        recommendationValidator.validateAuthor(savedRecommendation);
        log.info("Called delete, recommendationId={}", recommendationId);
        recommendationRepository.deleteById(recommendationId);
    }

    @Override
    public List<RecommendationDto> getByFilters(RecommendationFilterDto recommendationFilterDto) {
        Stream<Recommendation> recommendations = recommendationRepository.findAll().stream();

        for (RecommendationFilter filter : recommendationFilter) {
            if (filter.isApplicable(recommendationFilterDto)) {
                recommendations = filter.apply(recommendations, recommendationFilterDto);
            }
        }
        return recommendations.map(recommendationMapper::toRecommendationDto).toList();
    }

    private Recommendation findByIdOrElseThrow(long recommendationId) {
        return recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Recommendation with id " + recommendationId + " not found"));
    }
}














