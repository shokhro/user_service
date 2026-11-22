package school.faang.user_service.service.mentorship;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.mentorship.CreateMentorshipRequestDto;
import school.faang.user_service.dto.mentorship.MentorshipRequestDto;
import school.faang.user_service.dto.mentorship.MentorshipRequestFilterDto;
import school.faang.user_service.entity.MentorshipRequest;
import school.faang.user_service.filter.mentorshipRequest.MentorshipRequestFilter;
import school.faang.user_service.mapper.MentorshipRequestMapper;
import school.faang.user_service.repository.mentorship.MentorshipRequestRepository;
import school.faang.user_service.validator.mentorship.MentorshipRequestValidator;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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
    public void accept(long requesterId) {

    }


}
























