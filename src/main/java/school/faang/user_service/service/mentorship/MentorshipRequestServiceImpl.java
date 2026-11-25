package school.faang.user_service.service.mentorship;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.RejectionDto;
import school.faang.user_service.dto.mentorship.CreateMentorshipRequestDto;
import school.faang.user_service.dto.mentorship.MentorshipRequestDto;
import school.faang.user_service.dto.mentorship.MentorshipRequestFilterDto;
import school.faang.user_service.entity.MentorshipRequest;
import school.faang.user_service.entity.RequestStatus;
import school.faang.user_service.entity.User;
import school.faang.user_service.filter.mentorshipRequest.MentorshipRequestFilter;
import school.faang.user_service.mapper.MentorshipRequestMapper;
import school.faang.user_service.repository.mentorship.MentorshipRequestRepository;
import school.faang.user_service.validator.mentorship.MentorshipRequestValidator;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class MentorshipRequestServiceImpl implements MentorshipRequestService{

    private final MentorshipRequestRepository mentorshipRequestRepository;
    private final MentorshipRequestValidator mentorshipRequestValidator;
    private final MentorshipRequestMapper mentorshipRequestMapper;
    private final List<MentorshipRequestFilter> filters;
    private final UserContext userContext;

    @Override
    @Transactional
    public MentorshipRequestDto create(CreateMentorshipRequestDto requestDto) {
        long currentUserId = userContext.getUserId();
        long mentorId = requestDto.mentorId();
        mentorshipRequestValidator.validateCreate(currentUserId, mentorId);
        MentorshipRequest mentorshipRequest = mentorshipRequestRepository
                .create(currentUserId, mentorId, requestDto.description());
        return mentorshipRequestMapper.toDto(mentorshipRequest);
    }
    
    @Override
    public List<MentorshipRequestDto> getByFilter(MentorshipRequestFilterDto filterDto) {
        Stream<MentorshipRequest> requests = mentorshipRequestRepository.findAll().stream();

        for (MentorshipRequestFilter filter : filters) {
            if (filter.isApplicable(filterDto)) {
                requests = filter.apply(requests, filterDto);
            }
        }
        return requests.map(mentorshipRequestMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void accept(long mentorshipRequestId) {
        MentorshipRequest mentorshipRequest = findByIdOrElseThrow(mentorshipRequestId);
        mentorshipRequestValidator.validateAccept(mentorshipRequest, userContext.getUserId());
        User mentor = mentorshipRequest.getReceiver();
        User mentee = mentorshipRequest.getRequester();
        mentor.getMentees().add(mentee);
        mentorshipRequest.setStatus(RequestStatus.ACCEPTED);
        mentorshipRequestRepository.save(mentorshipRequest);
        log.info("Mentorship request {} accepted: mentor={}, mentee={}",
                mentorshipRequestId, mentor.getId(), mentee.getId());
    }

    @Override
    @Transactional
    public void reject(long mentorshipRequestId, RejectionDto rejectionDto) {
        MentorshipRequest mentorshipRequest = findByIdOrElseThrow(mentorshipRequestId);
        mentorshipRequestValidator.validateReject(mentorshipRequest, userContext.getUserId());
        mentorshipRequest.setStatus(RequestStatus.REJECTED);
        mentorshipRequest.setRejectionReason(rejectionDto.reason());
        mentorshipRequestRepository.save(mentorshipRequest);
        log.info("Mentorship request {} rejected: mentor={}, mentee={}",
                mentorshipRequestId,
                mentorshipRequest.getReceiver().getId(),
                mentorshipRequest.getRequester().getId());
    }

    private MentorshipRequest findByIdOrElseThrow(long mentorshipRequestId) {
        return mentorshipRequestRepository.findById(mentorshipRequestId).orElseThrow(() ->
                new EntityNotFoundException("Mentorship request with id %d not found".formatted(mentorshipRequestId)));
    }


}
























