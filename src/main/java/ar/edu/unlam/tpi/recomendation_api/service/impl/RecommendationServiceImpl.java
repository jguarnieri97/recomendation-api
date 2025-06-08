package ar.edu.unlam.tpi.recomendation_api.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;
import ar.edu.unlam.tpi.recomendation_api.models.RecommendationEntity;
import ar.edu.unlam.tpi.recomendation_api.dto.request.RecommendationRequest;
import ar.edu.unlam.tpi.recomendation_api.dto.response.RecommendationResponse;
import ar.edu.unlam.tpi.recomendation_api.exceptions.NotFoundException;
import ar.edu.unlam.tpi.recomendation_api.persistence.dao.RecommendationDAO;
import ar.edu.unlam.tpi.recomendation_api.service.RecommendationService;
import ar.edu.unlam.tpi.recomendation_api.utils.CategoryConfig;
import ar.edu.unlam.tpi.recomendation_api.utils.RecommendationConverter;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationDAO dao;
    private final CategoryConfig config;
    private final RecommendationConverter converter;

    @Value("${recommendation.threshold:0.7}")
    private double threshold;
    @Override
    public void createRecommendation(RecommendationRequest request) {
        log.debug("Creando recomendaciones para applicantId {}", request.getApplicantId());
    
        List<RecommendationEntity> toSave = request.getTags().stream()
                .peek(tag -> log.debug(" Revisando tag: {} con probabilidad {}", tag.getTagName(), tag.getProbability()))
                .filter(tag -> {
                    boolean isAboveThreshold = tag.getProbability() >= threshold;
                    log.debug(" Pasa threshold {}: {}", threshold, isAboveThreshold);
                    return isAboveThreshold;
                })
                .map(tag -> {
                    Optional<String> categoria = config.getCategoryByTag(tag.getTagName());
                    if (categoria.isEmpty()) {
                        log.debug("Tag '{}' no tiene categoría configurada", tag.getTagName());
                    } else {
                        log.debug("Tag '{}' pertenece a categoría '{}'", tag.getTagName(), categoria.get());
                    }
                    return categoria.map(cat -> converter.convertToEntity(request.getApplicantId(), tag)).orElse(null);
                })
                .filter(Objects::nonNull)
                .toList();
    
        log.debug("💾 Cantidad de recomendaciones a guardar: {}", toSave.size());
        toSave.forEach(r -> log.debug("💾 Guardando: applicantId={}, tag={}, prob={}", r.getApplicantId(), r.getTag(), r.getProbability()));
    
        dao.saveAll(toSave);
    }
    

    @Override
public List<RecommendationResponse> getRecommendations(Long applicantId, int limit) {
    Pageable pageable = PageRequest.of(0, limit);
    List<RecommendationEntity> entities = dao.findByApplicantIdOrderByCreatedAtDesc(applicantId, pageable);

    if (entities.isEmpty()) {
        throw new NotFoundException("No recommendations found for applicantId: " + applicantId);
    }

    return entities.stream()
            .map(rec -> {
                String cat = config.getCategoryByTag(rec.getTag()).orElse("UNKNOWN");
                return converter.convertToResponse(rec, cat);
            })
            .toList();
}

}
