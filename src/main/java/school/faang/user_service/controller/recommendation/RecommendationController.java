package school.faang.user_service.controller.recommendation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.dto.recommendation.CreateRecommendationDto;
import school.faang.user_service.dto.recommendation.RecommendationDto;
import school.faang.user_service.dto.recommendation.RecommendationFilterDto;
import school.faang.user_service.dto.recommendation.UpdateRecommendationDto;
import school.faang.user_service.service.recommendation.RecommendationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recommendations")
public class RecommendationController{
    private final RecommendationService recommendationService;

    @PostMapping
    public ResponseEntity<RecommendationDto> create(
            @RequestBody @Valid CreateRecommendationDto recommendationDto
    ) {
        RecommendationDto result = recommendationService.create(recommendationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{recommendationId}")
    public ResponseEntity<RecommendationDto> update(
            @PathVariable long recommendationId,
            @RequestBody @Valid UpdateRecommendationDto recommendationDto
    ) {
        RecommendationDto updated
                = recommendationService.update(recommendationId, recommendationDto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{recommendationId}")
    public ResponseEntity<Void> delete(@PathVariable long recommendationId) {
        recommendationService.delete(recommendationId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<List<RecommendationDto>> getByFilters(
            @RequestBody @Valid RecommendationFilterDto filters)
    {
        List<RecommendationDto> result = recommendationService.getByFilters(filters);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
