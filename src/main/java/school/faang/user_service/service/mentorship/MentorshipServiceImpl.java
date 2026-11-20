package school.faang.user_service.service.mentorship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.User;
import school.faang.user_service.mapper.UserMapper;
import school.faang.user_service.repository.mentorship.MentorshipRepository;
import school.faang.user_service.validator.mentorship.MentorshipValidator;
import school.faang.user_service.validator.user.UserValidator;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MentorshipServiceImpl implements MentorshipService {

    private final MentorshipRepository mentorshipRepository;
    private final MentorshipValidator mentorshipValidator;
    private final UserValidator userValidator;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void addMentorship(long mentorId, long menteeId) {
        mentorshipValidator.validateMentorAndMenteeDiffer(mentorId, menteeId);
        User mentor = userValidator.findByIdOrElseThrows(mentorId);
        User mentee = userValidator.findByIdOrElseThrows(menteeId);
        boolean alreadyExists = mentee.getMentors().stream()
                .anyMatch(m -> m.getId() == mentorId);
        if (!alreadyExists) {
            mentee.getMentors().add(mentor);
            mentorshipRepository.save(mentee);
            log.info("Mentorship saved, mentorId={}, menteeId={}", mentorId, menteeId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getMentees(long userId) {
        User user = userValidator.findByIdOrElseThrows(userId);
        return user.getMentees().stream().map(userMapper::toUserDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getMentors(long userId) {
        User user = userValidator.findByIdOrElseThrows(userId);
        return user.getMentors().stream().map(userMapper::toUserDto).toList();
    }

    @Override
    @Transactional
    public void deleteMentorship(long menteeId, long mentorId) {
        mentorshipValidator.validateCanDeleteMentorship(menteeId, mentorId);
        User mentee = userValidator.findByIdOrElseThrows(menteeId);
        boolean removed = mentee.getMentors()
                .removeIf(mentor -> mentor.getId() == mentorId);

        if (removed) {
            mentorshipRepository.save(mentee);
            log.info("Mentorship deleted, mentorId={}, menteeId={}", mentorId, menteeId);
        }
    }
}














