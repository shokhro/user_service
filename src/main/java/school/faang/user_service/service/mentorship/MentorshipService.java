package school.faang.user_service.service.mentorship;

import school.faang.user_service.dto.user.UserDto;

import java.util.List;

public interface MentorshipService{

    void addMentorship(long mentorId, long menteeId);

    List<UserDto> getMentees(long userId);

    List<UserDto> getMentors(long userId);

    void deleteMentorship(long menteeId, long mentorId);
}
