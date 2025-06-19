package ar.edu.unlam.tpi.recomendation_api.models;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "RECOMMENDATIONS", schema = "RECOM")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "applicant_id", nullable = false)
    private Long applicantId;

    private double probability;

    private String tag;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
