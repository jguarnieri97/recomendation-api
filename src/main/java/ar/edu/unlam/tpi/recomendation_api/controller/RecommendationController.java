package ar.edu.unlam.tpi.recomendation_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ar.edu.unlam.tpi.recomendation_api.dto.request.RecommendationRequest;
import ar.edu.unlam.tpi.recomendation_api.dto.response.GenericResponse;
import ar.edu.unlam.tpi.recomendation_api.dto.response.RecommendationResponse;
import jakarta.validation.Valid;


@RequestMapping("/recomendation-api/v1")
@Validated
public interface RecommendationController {

    @PostMapping("/recommendations")
    @ResponseStatus(HttpStatus.CREATED)
    GenericResponse<Void> create(@Valid @RequestBody RecommendationRequest request);

    @GetMapping("/recommendations")
    @ResponseStatus(HttpStatus.OK)
    GenericResponse<List<RecommendationResponse>> get(
        @RequestParam Long applicantId,
        @RequestParam(defaultValue = "3") int limit);
}
