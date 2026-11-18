package school.faang.user_service.service.recommendation.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.recommendation.CreateRecommendationRequestDto;
import school.faang.user_service.dto.recommendation.RecommendationRequestDto;
import school.faang.user_service.dto.recommendation.RecommendationRequestFilterDto;
import school.faang.user_service.dto.recommendation.RejectionDto;
import school.faang.user_service.entity.RequestStatus;
import school.faang.user_service.entity.User;
import school.faang.user_service.entity.recommendation.RecommendationRequest;
import school.faang.user_service.filter.recommendationRequest.RecommendationRequestFilter;
import school.faang.user_service.mapper.RecommendationRequestMapper;
import school.faang.user_service.repository.recommendation.RecommendationRequestRepository;
import school.faang.user_service.service.recommendation.RecommendationRequestService;
import school.faang.user_service.validator.recommendation.RecommendationRequestValidator;
import school.faang.user_service.validator.user.UserValidator;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationRequestServiceImpl implements RecommendationRequestService {

    private final RecommendationRequestRepository recommendationRequestRepository;
    private final RecommendationRequestMapper recommendationRequestMapper;
    private final RecommendationRequestValidator recommendationRequestValidator;
    private final List<RecommendationRequestFilter> filters;
    private final UserContext userContext;
    private final UserValidator userValidator;

    @Override
    public RecommendationRequestDto create(CreateRecommendationRequestDto recommendationRequestDto) {
        long requesterId = userContext.getUserId();
        long receiverId = recommendationRequestDto.receiverId();
        recommendationRequestValidator.validateNotSelf(requesterId, receiverId);
        recommendationRequestValidator.validateInterval(requesterId, receiverId);

        User receiver = userValidator.findByIdOrElseThrows(receiverId);
        User requester = userValidator.findByIdOrElseThrows(requesterId);

        RecommendationRequest recommendationRequest =
                recommendationRequestMapper.toEntity(recommendationRequestDto);
        recommendationRequest.setReceiver(receiver);
        recommendationRequest.setRequester(requester);
        recommendationRequest.setStatus(RequestStatus.PENDING);

        RecommendationRequest saved = recommendationRequestRepository.save(recommendationRequest);
        log.info("Recommendation request created: id={}, requesterId={}, receiverId={}",
                saved.getId(), requesterId, receiverId);
        return recommendationRequestMapper.toDto(saved);
    }

    @Override
    public List<RecommendationRequestDto> getByFilters(RecommendationRequestFilterDto filterDto) {
        Stream<RecommendationRequest> recommendationRequesStream =
                recommendationRequestRepository.findAll().stream();

        for (RecommendationRequestFilter filter : filters) {
            if (filter.isApplicable(filterDto)) {
                recommendationRequesStream = filter.apply(recommendationRequesStream, filterDto);
            }
        }
        return recommendationRequesStream.map(recommendationRequestMapper::toDto).toList();
    }

    @Override
    public RecommendationRequestDto getById(long id) {
        RecommendationRequest recommendationRequest = recommendationRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found, id: " + id));
        return recommendationRequestMapper.toDto(recommendationRequest);
    }

    @Override
    @Transactional
    public void accept(long id) {
        RecommendationRequest recommendationRequest = getRequestForResponse(id);
        recommendationRequest.setStatus(RequestStatus.ACCEPTED);
        recommendationRequestRepository.save(recommendationRequest);
        log.info("Recommendation request accepted: id={}, requesterId={}, receiverId={}",
                id, recommendationRequest.getRequester().getId(), recommendationRequest.getReceiver().getId());
    }

    @Override
    @Transactional
    public void reject(long id, RejectionDto rejectionDto) {
        RecommendationRequest recommendationRequest = getRequestForResponse(id);
        recommendationRequest.setStatus(RequestStatus.REJECTED);
        recommendationRequest.setRejectionReason(rejectionDto.reason());
        recommendationRequestRepository.save(recommendationRequest);
        log.info("Recommendation request rejected: id={}, requesterId={}, receiverId={}",
                id, recommendationRequest.getRequester().getId(), recommendationRequest.getReceiver().getId());
    }

    private RecommendationRequest getRequestForResponse(long id) {
        RecommendationRequest recommendationRequest
                = recommendationRequestValidator.findByIdOrElseThrows(id);
        recommendationRequestValidator.validateCurrentUserIsReceiver(recommendationRequest);
        recommendationRequestValidator.validateStatusIsPending(recommendationRequest);
        return recommendationRequest;
    }
}





















