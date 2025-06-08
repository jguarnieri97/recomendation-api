package ar.edu.unlam.tpi.recomendation_api.controller.impl;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlam.tpi.recomendation_api.controller.RecommendationController;
import ar.edu.unlam.tpi.recomendation_api.dto.request.RecommendationRequest;
import ar.edu.unlam.tpi.recomendation_api.dto.response.GenericResponse;
import ar.edu.unlam.tpi.recomendation_api.dto.response.RecommendationResponse;
import ar.edu.unlam.tpi.recomendation_api.service.RecommendationService;
import ar.edu.unlam.tpi.recomendation_api.utils.Constants;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RecommendationControllerImpl implements RecommendationController {

    private final RecommendationService service;

    @Override
    public GenericResponse<Void> create(RecommendationRequest request) {
        service.createRecommendation(request);
        return new GenericResponse<>(Constants.STATUS_CREATED, Constants.CREATED_MESSAGE, null);
    }

    @Override
    public GenericResponse<List<RecommendationResponse>> get(Long applicantId, int limit) {
        List<RecommendationResponse> result = service.getRecommendations(applicantId, limit);
        return new GenericResponse<>(Constants.STATUS_OK, Constants.SUCCESS_MESSAGE, result);
    }
}