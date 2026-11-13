package school.faang.user_service.controller.skill;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.skill.CreateSkillDto;
import school.faang.user_service.dto.skill.SkillCandidateDto;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.service.skill.SkillService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/skills")
public class SkillController {

    private final SkillService skillService;
    private final UserContext userContext;

    @PostMapping
    public ResponseEntity<SkillDto> create(@Valid @RequestBody CreateSkillDto skillDto) {
        SkillDto result = skillService.create(skillDto);
        return ResponseEntity.status(201).body(result);
    }

    @GetMapping("/offered")
    public ResponseEntity<List<SkillCandidateDto>> getOfferedSkills() {
        List<SkillCandidateDto> offeredSkills =
                skillService.getOfferedSkills(userContext.getUserId());
        return ResponseEntity.status(200).body(offeredSkills);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<SkillDto>> getByUserId(@PathVariable Long userId) {
        List<SkillDto> result = skillService.getByUserId(userId);
        return ResponseEntity.status(200).body(result);
    }

    @PostMapping("/{skillId}/acquire")
    public ResponseEntity<Void> acquireSkillFromOffers(@PathVariable Long skillId) {
        skillService.acquireSkillFromOffers(skillId, userContext.getUserId());
        return ResponseEntity.ok().build();
    }


}
