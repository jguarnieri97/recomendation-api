package ar.edu.unlam.tpi.recomendation_api.utils;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import ar.edu.unlam.tpi.recomendation_api.dto.request.TagRequest;
import ar.edu.unlam.tpi.recomendation_api.dto.response.RecommendationResponse;
import ar.edu.unlam.tpi.recomendation_api.models.RecommendationEntity;

@Component
public class RecommendationConverter {

    public RecommendationResponse convertToResponse(RecommendationEntity entity, String category) {
        return RecommendationResponse.builder()
                .applicantId(entity.getApplicantId())
                .category(category)
                .label(entity.getTag())
                .build();
    }

    public RecommendationEntity convertToEntity(Long applicantId, TagRequest tag) {
        return RecommendationEntity.builder()
                .applicantId(applicantId)
                .tag(tag.getTagName())
                .probability(tag.getProbability())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
