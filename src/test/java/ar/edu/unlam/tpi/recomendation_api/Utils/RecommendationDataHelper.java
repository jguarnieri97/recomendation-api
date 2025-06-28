package ar.edu.unlam.tpi.recomendation_api.Utils;

import java.time.LocalDateTime;
import java.util.List;

import ar.edu.unlam.tpi.recomendation_api.dto.request.RecommendationRequest;
import ar.edu.unlam.tpi.recomendation_api.dto.request.TagRequest;
import ar.edu.unlam.tpi.recomendation_api.dto.response.RecommendationResponse;
import ar.edu.unlam.tpi.recomendation_api.models.RecommendationEntity;
import lombok.Builder;

@Builder
public class RecommendationDataHelper {

    public static RecommendationRequest buildValidRequest() {
        TagRequest tag = new TagRequest();
        tag.setProbability(0.95);
        tag.setTagName("office_cleaning");

        RecommendationRequest request = new RecommendationRequest();
        request.setApplicantId(1L);
        request.setTags(List.of(tag));
        return request;
    }

    public static RecommendationEntity buildEntity() {
        RecommendationEntity entity = new RecommendationEntity();
        entity.setId(1L);
        entity.setApplicantId(1L);
        entity.setProbability(0.95);
        entity.setTag("office_cleaning");
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }

    public static RecommendationResponse buildResponse() {
        RecommendationResponse response = new RecommendationResponse();
        response.setApplicantId(1L);
        response.setCategory("CLEANING");
        response.setLabel("office_cleaning");
        return response;
    }
}
