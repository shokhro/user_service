package school.faang.user_service.service.mentorship;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.mentorship.CreateMentorshipRequestDto;
import school.faang.user_service.dto.mentorship.MentorshipRequestDto;
import school.faang.user_service.entity.MentorshipRequest;
import school.faang.user_service.mapper.MentorshipRequestMapper;
import school.faang.user_service.repository.mentorship.MentorshipRequestRepository;
import school.faang.user_service.validator.mentorship.MentorshipRequestValidator;

@Service
@RequiredArgsConstructor
public class MentorshipRequestServiceImpl implements MentorshipRequestService{

    private final MentorshipRequestRepository mentorshipRequestRepository;
    private final MentorshipRequestValidator mentorshipRequestValidator;
    private final MentorshipRequestMapper mentorshipRequestMapper;
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
}
