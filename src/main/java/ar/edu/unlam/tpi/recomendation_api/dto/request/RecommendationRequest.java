package ar.edu.unlam.tpi.recomendation_api.dto.request;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationRequest {
    private Long applicantId;
    private List<TagRequest> tags;
}