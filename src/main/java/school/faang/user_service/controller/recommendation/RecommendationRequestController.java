package school.faang.user_service.controller.recommendation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.dto.recommendation.CreateRecommendationRequestDto;
import school.faang.user_service.dto.recommendation.RecommendationRequestDto;
import school.faang.user_service.dto.recommendation.RecommendationRequestFilterDto;
import school.faang.user_service.dto.RejectionDto;
import school.faang.user_service.service.recommendation.RecommendationRequestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recommendation-request")
public class RecommendationRequestController {
    private final RecommendationRequestService recommendationRequestService;

    @PostMapping
    public ResponseEntity<RecommendationRequestDto> create(
            @RequestBody @Valid CreateRecommendationRequestDto dto
    ) {
        RecommendationRequestDto recommendationRequestDto = recommendationRequestService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(recommendationRequestDto);
    }

    @GetMapping
    public ResponseEntity<List<RecommendationRequestDto>> getByFilters(
            @RequestBody @Valid RecommendationRequestFilterDto filterDto
    ) {
        List<RecommendationRequestDto> byFilters = recommendationRequestService.getByFilters(filterDto);
        return ResponseEntity.status(HttpStatus.OK).body(byFilters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecommendationRequestDto> getById(@PathVariable long id) {
        RecommendationRequestDto result = recommendationRequestService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping("/{id}/accept")
    public ResponseEntity<Void> accept(@PathVariable long id) {
        recommendationRequestService.accept(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<Void> reject(
            @PathVariable long id,
            @RequestBody @Valid RejectionDto rejectionDto
    ) {
        recommendationRequestService.reject(id, rejectionDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
