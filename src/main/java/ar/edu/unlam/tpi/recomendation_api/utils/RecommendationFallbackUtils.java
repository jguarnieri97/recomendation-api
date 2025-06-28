package ar.edu.unlam.tpi.recomendation_api.utils;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import ar.edu.unlam.tpi.recomendation_api.persistence.dao.RecommendationDAO;
import ar.edu.unlam.tpi.recomendation_api.models.RecommendationEntity;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RecommendationFallbackUtils {

    private final RecommendationDAO dao;

    public RecommendationEntity createDefaultCleaningRecommendation(Long applicantId) {
        RecommendationEntity entity = RecommendationEntity.builder()
                .applicantId(applicantId)
                .tag("office_cleaning")
                .probability(1.0)
                .createdAt(LocalDateTime.now())
                .build();

        return dao.save(entity);
    }
}