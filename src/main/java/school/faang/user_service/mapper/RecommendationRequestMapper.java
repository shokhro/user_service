package school.faang.user_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import school.faang.user_service.dto.recommendation.CreateRecommendationRequestDto;
import school.faang.user_service.dto.recommendation.RecommendationRequestDto;
import school.faang.user_service.entity.recommendation.RecommendationRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecommendationRequestMapper {

    RecommendationRequest toEntity(CreateRecommendationRequestDto dto);

    @Mapping(source = "requester", target = "requester")
    @Mapping(source = "receiver", target = "receiver")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy:MM:dd HH:mm:ss")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy:MM:dd HH:mm:ss")
    RecommendationRequestDto toDto(RecommendationRequest entity);
}
