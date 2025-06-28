package ar.edu.unlam.tpi.recomendation_api.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationRequest {
    @NotNull(message = "Applicant ID cannot be null")
    private Long applicantId;
    @NotEmpty(message = "Tags cannot be empty")
    private List<TagRequest> tags;
}