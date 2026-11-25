package school.faang.user_service.controller.mentorship;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.dto.RejectionDto;
import school.faang.user_service.dto.mentorship.CreateMentorshipRequestDto;
import school.faang.user_service.dto.mentorship.MentorshipRequestDto;
import school.faang.user_service.dto.mentorship.MentorshipRequestFilterDto;
import school.faang.user_service.service.mentorship.MentorshipRequestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mentorship-requests")
public class MentorshipRequestController {

    private final MentorshipRequestService mentorshipRequestService;

    @PostMapping
    public ResponseEntity<MentorshipRequestDto> create(
            @RequestBody @Valid CreateMentorshipRequestDto requestDto
    ) {
        MentorshipRequestDto mentorshipRequestDto = mentorshipRequestService.create(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mentorshipRequestDto);
    }

    @GetMapping
    public ResponseEntity<List<MentorshipRequestDto>> getByFilters(
            @Valid @ModelAttribute MentorshipRequestFilterDto filterDto
    ) {
        List<MentorshipRequestDto> result = mentorshipRequestService.getByFilter(filterDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping("/accept/{mentorshipRequestId}")
    public ResponseEntity<Void> accept(@PathVariable long mentorshipRequestId) {
        mentorshipRequestService.accept(mentorshipRequestId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/reject/{mentorshipRequestId}")
    public ResponseEntity<Void> reject(
            @PathVariable long mentorshipRequestId,
            @RequestBody @Valid RejectionDto rejectionDto
    ) {
        mentorshipRequestService.reject(mentorshipRequestId, rejectionDto);
        return ResponseEntity.ok().build();
    }

}
