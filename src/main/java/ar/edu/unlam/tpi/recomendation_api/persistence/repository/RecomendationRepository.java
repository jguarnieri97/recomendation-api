package ar.edu.unlam.tpi.recomendation_api.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;

import ar.edu.unlam.tpi.recomendation_api.models.RecommendationEntity;

public interface RecomendationRepository extends JpaRepository<RecommendationEntity, Long> {
    List<RecommendationEntity> findByApplicantIdOrderByCreatedAtDesc(Long applicantId, Pageable pageable);
}