package school.faang.user_service.controller.mentorship;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.service.mentorship.MentorshipService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mentorship")
public class MentorshipController {

    private final MentorshipService mentorshipService;

    @PostMapping("/mentors/{mentorId}/mentees/{menteeId}")
    public ResponseEntity<Void> addMentorship(
            @PathVariable("mentorId") long mentorId,
            @PathVariable("menteeId") long menteeId
    ) {
        mentorshipService.addMentorship(mentorId, menteeId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/mentors/{id}/mentees")
    public ResponseEntity<List<UserDto>> getMentees(@PathVariable("id") long userId) {
        List<UserDto> mentees = mentorshipService.getMentees(userId);
        return ResponseEntity.status(HttpStatus.OK).body(mentees);
    }

    @GetMapping("/mentees/{id}/mentors")
    public ResponseEntity<List<UserDto>> getMentors(@PathVariable("id") long userId) {
        List<UserDto> mentors = mentorshipService.getMentors(userId);
        return ResponseEntity.status(HttpStatus.OK).body(mentors);
    }

    @DeleteMapping("/mentors/{mentorId}/mentees/{menteeId}")
    public ResponseEntity<Void> deleteMentorship(
            @PathVariable("menteeId") long menteeId,
            @PathVariable("mentorId") long mentorId
    ) {
        mentorshipService.deleteMentorship(menteeId, mentorId);
        return ResponseEntity.ok().build();
    }

}
