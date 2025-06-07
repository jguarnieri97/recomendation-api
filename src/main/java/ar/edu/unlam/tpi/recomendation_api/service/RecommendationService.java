package ar.edu.unlam.tpi.recomendation_api.service;

import java.util.List;

import ar.edu.unlam.tpi.recomendation_api.dto.request.RecommendationRequest;
import ar.edu.unlam.tpi.recomendation_api.dto.response.RecommendationResponse;

public interface RecommendationService {
    void createRecommendation(RecommendationRequest request);

    List<RecommendationResponse> getRecommendations(Long applicantId, int limit);
}
