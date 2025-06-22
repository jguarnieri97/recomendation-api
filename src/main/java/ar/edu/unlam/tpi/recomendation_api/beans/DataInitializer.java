package ar.edu.unlam.tpi.recomendation_api.beans;

import ar.edu.unlam.tpi.recomendation_api.models.RecommendationEntity;
import ar.edu.unlam.tpi.recomendation_api.persistence.dao.RecommendationDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "test", matchIfMissing = true)
public class DataInitializer implements CommandLineRunner {

    private final RecommendationDAO recommendationDAO;

    @Override
    public void run(String... args) {
        RecommendationEntity contractor1 = RecommendationEntity.builder()
                .applicantId(1L)
                .probability(0.95)
                .tag("roof_repair")
                .createdAt(LocalDateTime.of(2025, 6, 7, 12, 30))
                .build();

        RecommendationEntity electrician1 = RecommendationEntity.builder()
                .applicantId(1L)
                .probability(0.90)
                .tag("electrical_wiring_repair")
                .createdAt(LocalDateTime.of(2025, 6, 7, 12, 45))
                .build();

        RecommendationEntity cleaning1 = RecommendationEntity.builder()
                .applicantId(2L)
                .probability(0.92)
                .tag("office_cleaning")
                .createdAt(LocalDateTime.of(2025, 6, 6, 17, 15))
                .build();

        recommendationDAO.saveAll(Arrays.asList(contractor1, electrician1, cleaning1));

        log.info("✅ Datos de recomendaciones inicializados correctamente");
    }
}